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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _sortOption = MutableStateFlow(SortOption.NEWEST)
    private val _filters = MutableStateFlow(SearchFilters())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val resultsFlow = _query.flatMapLatest { query ->
        if (query.isBlank()) {
            comicRepository.getAllComics()
        } else {
            comicRepository.searchComics(query.trim())
        }
    }

    private val optionsFlow = comicRepository.getAllComics().map { all ->
        FilterOptions(
            genres = all.map { it.genre }.distinct().sorted(),
            authors = all.map { it.author }.distinct().sorted(),
            years = all.map { it.releaseDate.take(4) }.distinct().sortedDescending(),
        )
    }

    val uiState: StateFlow<SearchUiState> = combine(
        _query,
        resultsFlow,
        optionsFlow,
        _sortOption,
        _filters,
    ) { query, comics, options, sortOption, filters ->
//        throw RuntimeException("test error")
        val filtered = comics.filter { it.matches(filters) }
        SearchUiState.Content(
            query = query,
            suggestions = buildSuggestions(comics, options.genres, query.trim()),
            recentResults = filtered.sortedWith(sortOption.comparator()).map { it.toUi() },
            hotResultIds = filtered.filter { it.isFeatured }.map { it.id }.toSet(),
            sortOption = sortOption,
            filters = filters,
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
        _query.value = query
    }

    fun onClearRecent() {
        _query.value = ""
    }

    fun onSortChange(option: SortOption) {
        _sortOption.value = option
    }

    fun onFiltersChange(filters: SearchFilters) {
        _filters.value = filters
    }

    fun onClearFilters() {
        _filters.value = SearchFilters()
    }

    fun onSuggestionClick(suggestion: SearchSuggestion) {
        when (suggestion.type) {
            SuggestionType.TITLE, SuggestionType.AUTHOR -> _query.value = suggestion.text
            SuggestionType.GENRE -> {
                _filters.value = _filters.value.copy(genres = _filters.value.genres + suggestion.text)
                _query.value = ""
            }
        }
    }
}

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
    if (f.genres.isNotEmpty() && genre !in f.genres) return false
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
