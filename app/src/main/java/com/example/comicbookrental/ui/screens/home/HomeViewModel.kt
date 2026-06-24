package com.example.comicbookrental.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.repositories.comic.ComicRepository
import com.example.comicbookrental.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
) : ViewModel() {

    private val sectionsFlow: Flow<HomeData> = combine(
        comicRepository.getFeaturedComics(),
        comicRepository.getNewReleases(),
        comicRepository.getTopRated(),
        comicRepository.getGenres(),
    ) { featured, newReleases, topRated, genres ->
        HomeData(
            featured = featured.map { it.toUi() },
            newReleases = newReleases.map { it.toUi() },
            genres = genres,
            topRated = topRated.map { it.toUi() },
        )
    }

    val uiState: StateFlow<HomeUiState> =
        sectionsFlow
            .map<HomeData, HomeUiState> { HomeUiState.Content(sections = it) }
            .catch { e -> emit(HomeUiState.Error(e.message ?: "Couldn't load comics")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading,
            )

    init {
        viewModelScope.launch { comicRepository.seedIfEmpty() }
    }
}
