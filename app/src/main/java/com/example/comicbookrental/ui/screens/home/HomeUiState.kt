package com.example.comicbookrental.ui.screens.home

import com.example.comicbookrental.ui.model.ComicUi

data class HomeData(
    val featured: List<ComicUi>,
    val newReleases: List<ComicUi>,
    val genres: List<String>,
    val topRated: List<ComicUi>,
)


sealed interface HomeUiState {
    data object Loading : HomeUiState


    data class Content(
        val sections: HomeData,
        val selectedGenre: String? = null,
    ) : HomeUiState

    data class Error(val message: String) : HomeUiState
}
