package com.example.comicbookrental.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.domain.repository.ComicRepository
import com.example.comicbookrental.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
) : ViewModel() {

    private val input = MutableStateFlow(SearchInput())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val resultsFlow = input
        .map { it.query }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                comicRepository.getAllComics()
            } else {
                comicRepository.searchComics(query.trim())
            }
        }

    private val optionsFlow = comicRepository.getAllComics().map { all ->
        FilterOptions(
            genres = all.flatMap { it.genres }.distinct().sorted(),
            authors = all.map { it.author }.distinct().sorted(),
            years = all.map { it.releaseDate.take(4) }.distinct().sortedDescending(),
        )
    }

    val uiState: StateFlow<SearchUiState> = combine(
        input,
        resultsFlow,
        optionsFlow,
    ) { current, comics, options ->
        val filtered = comics.filter { it.matches(current.filters) }
        SearchUiState.Content(
            query = current.query,
            suggestions = buildSuggestions(comics, options.genres, current.query.trim()),
            recentResults = filtered.sortedWith(current.sortOption.comparator()).map { it.toUi() },
            hotResultIds = filtered.filter { it.isFeatured }.map { it.id }.toSet(),
            sortOption = current.sortOption,
            filters = current.filters,
            availableGenres = options.genres,
            availableAuthors = options.authors,
            availableYears = options.years,
        )
    }
        .map<SearchUiState.Content, SearchUiState> { it }   // widen so catch can emit Error
        .catch { e -> emit(SearchUiState.Error(e.message ?: "Couldn't load comics")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState.Loading,
        )

    fun onQueryChange(query: String) {
        input.update { it.copy(query = query) }
    }

    fun onClearRecent() {
        input.update { it.copy(query = "") }
    }

    fun onSortChange(option: SortOption) {
        input.update { it.copy(sortOption = option) }
    }

    fun onFiltersChange(filters: SearchFilters) {
        input.update { it.copy(filters = filters) }
    }

    fun onClearFilters() {
        input.update { it.copy(filters = SearchFilters()) }
    }

    fun onSuggestionClick(suggestion: SearchSuggestion) {
        when (suggestion.type) {
            SuggestionType.TITLE, SuggestionType.AUTHOR ->
                input.update { it.copy(query = suggestion.text) }

            SuggestionType.GENRE ->
                input.update {
                    it.copy(
                        filters = it.filters.copy(genres = it.filters.genres + suggestion.text),
                        query = "",
                    )
                }
        }
    }
}

private data class SearchInput(
    val query: String = "",
    val sortOption: SortOption = SortOption.NEWEST,
    val filters: SearchFilters = SearchFilters(),
)

private const val SUGGESTION_LIMIT = 8

private fun buildSuggestions(
    comics: List<Comic>,
    genres: List<String>,
    query: String,
): List<SearchSuggestion> {
    if (query.isBlank()) return emptyList()

    fun String.hits() = contains(query, ignoreCase = true) && !equals(query, ignoreCase = true)

    val titles = comics.map { it.title }.filter { it.hits() }.distinct()
        .map { SearchSuggestion(it, SuggestionType.TITLE) }
    val authors = comics.map { it.author }.filter { it.hits() }.distinct()
        .map { SearchSuggestion(it, SuggestionType.AUTHOR) }
    val genreHits = genres.filter { it.hits() }
        .map { SearchSuggestion(it, SuggestionType.GENRE) }

    return (titles + authors + genreHits).take(SUGGESTION_LIMIT)
}

private data class FilterOptions(
    val genres: List<String>,
    val authors: List<String>,
    val years: List<String>,
)

private fun Comic.matches(f: SearchFilters): Boolean {
    if (f.genres.isNotEmpty() && genres.none { it in f.genres }) return false
    if (f.authors.isNotEmpty() && author !in f.authors) return false
    f.minRating?.let { if ((avgRating.toFloatOrNull() ?: 0f) < it) return false }
    if (f.years.isNotEmpty() && releaseDate.take(4) !in f.years) return false
    if (f.popularOnly && viewCount < POPULAR_VIEW_THRESHOLD) return false
    return true
}

private fun SortOption.comparator(): Comparator<Comic> = when (this) {
    SortOption.ALPHABETICAL -> compareBy { it.title.lowercase() }
    SortOption.NEWEST -> compareByDescending { it.releaseDate }
    SortOption.HIGHEST_RATED -> compareByDescending { it.avgRating.toDoubleOrNull() ?: 0.0 }
    SortOption.MOST_RENTED -> compareByDescending { it.viewCount }
}
