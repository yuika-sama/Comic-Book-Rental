package com.example.comicbookrental.ui.screens.search

data class SearchFilters(
    val genres: Set<String> = emptySet(),
    val authors: Set<String> = emptySet(),
    val minRating: Float? = null,
    val years: Set<String> = emptySet(),
    val popularOnly: Boolean = false,
) {
    val activeCount: Int
        get() = genres.size + authors.size + years.size +
            (if (minRating != null) 1 else 0) + (if (popularOnly) 1 else 0)

    val isActive: Boolean get() = activeCount > 0
}

val RATING_FILTER_OPTIONS = listOf(4.8f, 4.5f, 4.0f, 3.0f)

const val POPULAR_VIEW_THRESHOLD = 30_000
