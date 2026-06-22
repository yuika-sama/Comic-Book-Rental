package com.example.comicbookrental.ui.screens.home

import com.example.comicbookrental.data.entities.ComicEntity

/** The data for the four Home sections (everything except the search box). */
data class HomeData(
    val featured: List<ComicEntity>,
    val newReleases: List<ComicEntity>,
    val genres: List<String>,
    val topRated: List<ComicEntity>,
)

/**
 * Everything the Home screen needs to render, as one closed set of states so the UI can
 * exhaustively `when` over it.
 */
sealed interface HomeUiState {

    /** First load in flight. */
    data object Loading : HomeUiState

    /**
     * Loaded.
     * @param sections the always-present section data.
     * @param searchResults non-null only while the user is searching; when set, the screen shows
     *   this flat result list instead of the sections.
     */
    data class Content(
        val sections: HomeData,
        val searchResults: List<ComicEntity>? = null,
    ) : HomeUiState

    /** Loading failed. */
    data class Error(val message: String) : HomeUiState
}
