package com.example.comicbookrental.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.comicbookrental.domain.repository.ComicRepository
import com.example.comicbookrental.ui.navigation.ComicDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    comicRepository: ComicRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val comicId: Int =
        savedStateHandle.toRoute<ComicDetailRoute>().comicId.toIntOrNull() ?: -1

    val uiState: StateFlow<ComicDetailUiState> = combine(
        comicRepository.getComicById(comicId),
        comicRepository.getReviewsForComic(comicId),
        comicRepository.getAllComics(),
    ) { comic, reviews, allComics ->
        if (comic == null) {
            ComicDetailUiState.NotFound
        } else {
            val similar = allComics
                .filter { other -> other.id != comic.id && other.genres.any { it in comic.genres } }
                .take(SIMILAR_LIMIT)
            ComicDetailUiState.Content(
                comic = comic,
                detail = comic.toDetailUi(reviews = reviews, similar = similar),
            )
        }
    }
        .catch { e -> emit(ComicDetailUiState.Error(e.message ?: "Couldn't load this comic")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ComicDetailUiState.Loading,
        )

    private companion object {
        const val SIMILAR_LIMIT = 6
    }
}
