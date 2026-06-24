package com.example.comicbookrental.ui.screens.detail

import com.example.comicbookrental.data.models.Comic

sealed interface ComicDetailUiState {
    data object Loading : ComicDetailUiState

    data class Content(val comic: Comic, val detail: ComicDetailUi) : ComicDetailUiState

    data object NotFound : ComicDetailUiState

    data class Error(val message: String) : ComicDetailUiState
}
