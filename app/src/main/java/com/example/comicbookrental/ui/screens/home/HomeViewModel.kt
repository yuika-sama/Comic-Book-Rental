package com.example.comicbookrental.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.repositories.comic.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Holds and exposes state for the Home screen.
 *
 * MVVM contract: the View only reads [uiState] and calls [onSearchQueryChange]; all data access
 * stays behind [ComicRepository].
 *
 * State is assembled from two reactive sources:
 *  - [sectionsFlow]: the four section flows combined into one [HomeData]; any DB change refreshes
 *    the relevant section automatically.
 *  - search: the current query mapped to results (or null when not searching).
 * The two are combined into [HomeUiState.Content].
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val sectionsFlow: Flow<HomeData> = combine(
        comicRepository.getFeaturedComics(),
        comicRepository.getNewReleases(),
        comicRepository.getTopRated(),
        comicRepository.getGenres(),
    ) { featured, newReleases, topRated, genres ->
        HomeData(
            featured = featured,
            newReleases = newReleases,
            genres = genres,
            topRated = topRated,
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchFlow: Flow<List<ComicEntity>?> = _searchQuery.flatMapLatest { query ->
        if (query.isBlank()) flowOf(null) else comicRepository.searchComics(query.trim())
    }

    val uiState: StateFlow<HomeUiState> =
        combine(sectionsFlow, searchFlow) { sections, searchResults ->
            HomeUiState.Content(sections = sections, searchResults = searchResults)
        }
            .map<HomeUiState.Content, HomeUiState> { it }   // upcast so catch can emit Error
            .catch { e -> emit(HomeUiState.Error(e.message ?: "Couldn't load comics")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading,
            )

    init {
        viewModelScope.launch { comicRepository.seedIfEmpty() }
    }

    /** Called by the search field on every keystroke. */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
