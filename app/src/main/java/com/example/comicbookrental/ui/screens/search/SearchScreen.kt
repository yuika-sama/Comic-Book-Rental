package com.example.comicbookrental.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.commonComponents.CartComicCover
import com.example.comicbookrental.ui.components.commonComponents.HazardBanner
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader
import com.example.comicbookrental.ui.components.searchComponents.ComicSearchField
import com.example.comicbookrental.ui.components.searchComponents.FilterBar
import com.example.comicbookrental.ui.components.searchComponents.FilterSheet
import com.example.comicbookrental.ui.components.searchComponents.SearchResultCard
import com.example.comicbookrental.ui.components.searchComponents.SearchSuggestions
import com.example.comicbookrental.ui.components.searchComponents.SortControl
import com.example.comicbookrental.ui.model.ComicUi
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens


@Composable
fun SearchRoute(
    onComicClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onComicClick = onComicClick,
        modifier = modifier,
        onSuggestionClick = viewModel::onSuggestionClick,
        onClearRecent = viewModel::onClearRecent,
        onSortChange = viewModel::onSortChange,
        onFiltersChange = viewModel::onFiltersChange,
        onClearFilters = viewModel::onClearFilters,
        onMenuClick = onMenuClick,
        onNotificationsClick = onNotificationsClick,
    )
}


@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onComicClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onSuggestionClick: (SearchSuggestion) -> Unit = {},
    onClearRecent: () -> Unit = {},
    onSortChange: (SortOption) -> Unit = {},
    onFiltersChange: (SearchFilters) -> Unit = {},
    onClearFilters: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
) {
    when (uiState) {
        is SearchUiState.Loading -> SearchStatus(modifier) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        is SearchUiState.Error -> SearchStatus(modifier) {
            HazardBanner(message = uiState.message, modifier = Modifier.fillMaxWidth())
        }

        is SearchUiState.Content -> SearchContent(
            content = uiState,
            onQueryChange = onQueryChange,
            onComicClick = onComicClick,
            onSuggestionClick = onSuggestionClick,
            onClearRecent = onClearRecent,
            onSortChange = onSortChange,
            onFiltersChange = onFiltersChange,
            onClearFilters = onClearFilters,
            modifier = modifier,
        )
    }
}

@Composable
private fun SearchStatus(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.Spacing.StackLg),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun SearchContent(
    content: SearchUiState.Content,
    onQueryChange: (String) -> Unit,
    onComicClick: (Int) -> Unit,
    onSuggestionClick: (SearchSuggestion) -> Unit,
    onClearRecent: () -> Unit,
    onSortChange: (SortOption) -> Unit,
    onFiltersChange: (SearchFilters) -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimens.Spacing.Margin),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
        ) {
            fullSpanItem {
                ComicSearchField(value = content.query, onValueChange = onQueryChange)
            }

            if (content.suggestions.isNotEmpty()) {
                fullSpanItem {
                    SearchSuggestions(
                        suggestions = content.suggestions,
                        onSuggestionClick = onSuggestionClick,
                    )
                }
            }

            fullSpanItem {
                FilterBar(
                    filters = content.filters,
                    onOpenSheet = { showFilterSheet = true },
                    onFiltersChange = onFiltersChange,
                )
            }

            fullSpanItem {
                SectionHeader(
                    title = "Recent Results",
                    actionLabel = if (content.recentResults.isNotEmpty()) "Clear All" else null,
                    onActionClick = if (content.recentResults.isNotEmpty()) onClearRecent else null,
                )
            }

            fullSpanItem {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    SortControl(selected = content.sortOption, onSelect = onSortChange)
                }
            }

            items(items = content.recentResults, key = { it.id }) { comic ->
                SearchResultCard(
                    title = comic.title,
                    genre = comic.genre,
                    rating = comic.rating,
                    isHot = comic.id in content.hotResultIds,
                    onClick = { onComicClick(comic.id) },
                    cover = { CartComicCover(url = comic.coverImageUrl, contentDescription = comic.title) },
                )
            }
        }
    }

    if (showFilterSheet) {
        FilterSheet(
            filters = content.filters,
            availableGenres = content.availableGenres,
            availableAuthors = content.availableAuthors,
            availableYears = content.availableYears,
            onFiltersChange = onFiltersChange,
            onClear = onClearFilters,
            onDismiss = { showFilterSheet = false },
        )
    }
}


private inline fun androidx.compose.foundation.lazy.grid.LazyGridScope.fullSpanItem(
    crossinline content: @Composable () -> Unit,
) = item(span = { GridItemSpan(maxLineSpan) }) { content() }

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, heightDp = 900, widthDp = 360)
@Composable
private fun SearchScreenPreview() {
    val sample = { id: Int, title: String, genre: String, rating: String ->
        ComicUi(
            id = id, title = title, coverImageUrl = "", genre = genre, author = "A. Vega",
            description = "", rating = rating, ratingsCount = 1200, priceLabel = "$2.99",
        )
    }
    ComicBookRentalTheme {
        SearchScreen(
            uiState = SearchUiState.Content(
                query = "ne",
                suggestions = listOf(
                    SearchSuggestion("Neon Drifter: Vol 1", SuggestionType.TITLE),
                    SearchSuggestion("A. Frost", SuggestionType.AUTHOR),
                ),
                recentResults = listOf(
                    sample(1, "Neon Drifter: Vol 1", "Sci-Fi", "4.9"),
                    sample(2, "Shadows of Sector 7", "Noir", "4.5"),
                    sample(3, "Kaiju Reckoning", "Action", "4.7"),
                    sample(4, "Starfall Run", "Sci-Fi", "4.8"),
                ),
                hotResultIds = setOf(2),
                filters = SearchFilters(genres = setOf("Sci-Fi"), minRating = 4.5f),
                availableGenres = listOf("Action", "Fantasy", "Mystery", "Noir", "Sci-Fi"),
                availableAuthors = listOf("A. Frost", "H. Miller", "M. Chen"),
                availableYears = listOf("2025", "2024"),
            ),
            onQueryChange = {},
            onComicClick = {},
        )
    }
}
