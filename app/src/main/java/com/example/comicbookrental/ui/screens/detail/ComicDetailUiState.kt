package com.example.comicbookrental.ui.screens.detail

sealed interface ComicDetailUiState {
    data object Loading : ComicDetailUiState

    data class Content(val detail: ComicDetailUi) : ComicDetailUiState

    data object NotFound : ComicDetailUiState

    data class Error(val message: String) : ComicDetailUiState
}
