package com.example.comicbookrental.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.authComponents.ComicCard
import com.example.comicbookrental.ui.components.searchComponents.ComicSearchField
import com.example.comicbookrental.ui.components.searchComponents.SearchResultCard
import com.example.comicbookrental.ui.components.SectionHeader
import com.example.comicbookrental.ui.components.TopBarIconButton
import com.example.comicbookrental.ui.components.cartComponents.CartComicCover
import com.example.comicbookrental.ui.model.ComicUi
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.components.CartComicCover

/**
 * Stateful entry point — wires [SearchViewModel] to the stateless [SearchScreen]. The nav graph
 * (built separately) just composes this. `onMenuClick`/`onNotificationsClick` are left for the
 * shell to fill in.
 */
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

/**
 * Dedicated global-search screen (replaces the inline search field that used to live on Home).
 * UI-first: state is hoisted so it can be driven by a ViewModel later. Filters, sort and
 * autocomplete (requirement 3.2) plug in as follow-up steps.
 */
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
    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        SearchTopBar(
            onMenuClick = onMenuClick,
            onNotificationsClick = onNotificationsClick,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Dimens.Spacing.Margin),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
        ) {
            fullSpanItem {
                ComicSearchField(value = uiState.query, onValueChange = onQueryChange)
            }

            if (uiState.suggestions.isNotEmpty()) {
                fullSpanItem {
                    SearchSuggestions(
                        suggestions = uiState.suggestions,
                        onSuggestionClick = onSuggestionClick,
                    )
                }
            }

            fullSpanItem {
                FilterBar(
                    filters = uiState.filters,
                    onOpenSheet = { showFilterSheet = true },
                    onFiltersChange = onFiltersChange,
                )
            }

            fullSpanItem {
                SectionHeader(
                    title = "Recent Results",
                    actionLabel = if (uiState.recentResults.isNotEmpty()) "Clear All" else null,
                    onActionClick = if (uiState.recentResults.isNotEmpty()) onClearRecent else null,
                )
            }

            fullSpanItem {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    SortControl(selected = uiState.sortOption, onSelect = onSortChange)
                }
            }

            items(items = uiState.recentResults, key = { it.id }) { comic ->
                SearchResultCard(
                    title = comic.title,
                    genre = comic.genre,
                    rating = comic.rating,
                    isHot = comic.id in uiState.hotResultIds,
                    onClick = { onComicClick(comic.id) },
                    cover = { CartComicCover(url = comic.coverImageUrl, contentDescription = comic.title) },
                )
            }
        }
    }

    if (showFilterSheet) {
        FilterSheet(
            filters = uiState.filters,
            availableGenres = uiState.availableGenres,
            availableAuthors = uiState.availableAuthors,
            availableYears = uiState.availableYears,
            onFiltersChange = onFiltersChange,
            onClear = onClearFilters,
            onDismiss = { showFilterSheet = false },
        )
    }
}

/** Brand top bar matching the Figma reference: menu, the "Panel Rush" wordmark, and notifications. */
@Composable
private fun SearchTopBar(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.Sizes.BottomBarHeight)
            .padding(horizontal = Dimens.Spacing.Margin),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        TopBarIconButton(
            icon = Icons.Filled.Menu,
            contentDescription = "Menu",
            onClick = onMenuClick,
        )
        Text(
            text = "Panel Rush".uppercase(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
        )
        TopBarIconButton(
            icon = Icons.Filled.Notifications,
            contentDescription = "Notifications",
            onClick = onNotificationsClick,
        )
    }
}

/** Inline autocomplete panel under the search field: title / author / genre matches (brief §3.2). */
@Composable
private fun SearchSuggestions(
    suggestions: List<SearchSuggestion>,
    onSuggestionClick: (SearchSuggestion) -> Unit,
    modifier: Modifier = Modifier,
) {
    ComicCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        suggestions.forEachIndexed { index, suggestion ->
            if (index > 0) {
                HorizontalDivider(
                    thickness = Dimens.Border.Hairline,
                    color = MaterialTheme.extendedColors.ink.copy(alpha = 0.15f),
                )
            }
            val icon = when (suggestion.type) {
                SuggestionType.TITLE -> Icons.Filled.Search
                SuggestionType.AUTHOR -> Icons.Outlined.Person
                SuggestionType.GENRE -> Icons.Filled.Tune
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(suggestion) }
                    .padding(
                        horizontal = Dimens.Spacing.ContentSpacing,
                        vertical = Dimens.Spacing.ListItemSpacing,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimens.Icon.Medium),
                )
                Text(
                    text = suggestion.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = suggestion.type.label.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/** Ink-bordered "Sort: X" button that opens a [DropdownMenu] of [SortOption]s (brief §3.2). */
@Composable
private fun SortControl(
    selected: SortOption,
    onSelect: (SortOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(Dimens.Radius.Button)
    val ink = MaterialTheme.extendedColors.ink

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(Dimens.Sizes.ButtonHeight)
                .clip(shape)
                .background(MaterialTheme.colorScheme.surface)
                .border(Dimens.Border.Standard, ink, shape)
                .clickable { expanded = true }
                .padding(horizontal = Dimens.Spacing.ContentSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = null,
                tint = ink,
                modifier = Modifier.size(Dimens.Icon.Small),
            )
            Text(
                text = "Sort: ${selected.label}".uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                tint = ink,
                modifier = Modifier.size(Dimens.Icon.Medium),
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            SortOption.values().forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.label.uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                            color = if (option == selected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                )
            }
        }
    }
}

/** Helper: a grid item that spans the full row width (for headers and the search field). */
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
            uiState = SearchUiState(
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
