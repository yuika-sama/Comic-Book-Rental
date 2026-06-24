package com.example.comicbookrental.ui.screens.search

import com.example.comicbookrental.ui.model.ComicUi

/**
 * Ways the results grid can be ordered (brief §3.2 "sort options"). [label] is the user-facing
 * text shown in the sort menu.
 */
enum class SortOption(val label: String) {
    ALPHABETICAL("A–Z"),
    NEWEST("Newest"),
    HIGHEST_RATED("Top Rated"),
    MOST_RENTED("Most Rented"),
}

/** What a [SearchSuggestion] points at — drives its icon and how a tap is handled. */
enum class SuggestionType(val label: String) {
    TITLE("Title"),
    AUTHOR("Author"),
    GENRE("Genre"),
}

/** A single autocomplete suggestion shown under the search field (brief §3.2). */
data class SearchSuggestion(val text: String, val type: SuggestionType)

/**
 * UI state for the Search screen. Sealed interface with Loading / Content / Error, matching
 * [com.example.comicbookrental.ui.screens.home.HomeUiState] so all screens stay consistent.
 */
sealed interface SearchUiState {

    data object Loading : SearchUiState

    data class Content(
        val query: String = "",
        val suggestions: List<SearchSuggestion> = emptyList(),
        val recentResults: List<ComicUi> = emptyList(),
        val hotResultIds: Set<Int> = emptySet(),
        val sortOption: SortOption = SortOption.NEWEST,
        val filters: SearchFilters = SearchFilters(),
        // Available filter options, derived from the catalog.
        val availableGenres: List<String> = emptyList(),
        val availableAuthors: List<String> = emptyList(),
        val availableYears: List<String> = emptyList(),
    ) : SearchUiState

    data class Error(val message: String) : SearchUiState
}
