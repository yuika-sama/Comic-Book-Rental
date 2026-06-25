package com.example.comicbookrental.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.domain.repository.ComicRepository
import com.example.comicbookrental.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
) : ViewModel() {

    private val selectedGenre = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUiState> =
        combine(
            comicRepository.getFeaturedComics(),
            comicRepository.getNewReleases(),
            comicRepository.getTopRated(),
            comicRepository.getGenres(),
            selectedGenre,
        ) { featured, newReleases, topRated, genres, selected ->
            val filteredTopRated =
                if (selected == null) topRated else topRated.filter { selected in it.genres }

            HomeUiState.Content(
                sections = HomeData(
                    featured = featured.map { it.toUi() },
                    newReleases = newReleases.map { it.toUi() },
                    genres = genres,
                    topRated = filteredTopRated.map { it.toUi() },
                ),
                selectedGenre = selected,
            )
        }
            .map<HomeUiState.Content, HomeUiState> { it }
            .catch { e -> emit(HomeUiState.Error(e.message ?: "Couldn't load comics")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading,
            )

    init {
        viewModelScope.launch { comicRepository.seedIfEmpty() }
    }

    fun onGenreSelect(genre: String) {
        selectedGenre.update { if (it == genre) null else genre }
    }
}
