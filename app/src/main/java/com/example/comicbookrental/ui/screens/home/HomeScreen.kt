package com.example.comicbookrental.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.ComicCard
import com.example.comicbookrental.ui.components.ComicCover
import com.example.comicbookrental.ui.components.ComicSearchField
import com.example.comicbookrental.ui.components.FeaturedCarousel
import com.example.comicbookrental.ui.components.NewReleasesSection
import com.example.comicbookrental.ui.components.PopularGenresSection
import com.example.comicbookrental.ui.components.TopRatedSection
import com.example.comicbookrental.ui.model.ComicUi
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun HomeRoute(
    onComicClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        searchQuery = query,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onComicClick = onComicClick,
    )
}


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onComicClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onViewAllNewReleases: () -> Unit = {},
    onGenreClick: (String) -> Unit = {},
    onExploreTopRated: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = Dimens.Spacing.Gutter),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.SectionSpacing),
    ) {
        item {
            ComicSearchField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
            )
        }

        when (uiState) {
            is HomeUiState.Loading -> item { LoadingState() }

            is HomeUiState.Error -> item { ErrorState(message = uiState.message) }

            is HomeUiState.Content -> {
                val results = uiState.searchResults
                if (results != null) {
                    // Searching: show a flat result list instead of the shelves.
                    if (results.isEmpty()) {
                        item { EmptyState(searchQuery = searchQuery) }
                    } else {
                        items(items = results, key = { it.id }) { comic ->
                            SearchResultItem(
                                comic = comic,
                                onClick = { onComicClick(comic.id) },
                                modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
                            )
                        }
                    }
                } else {
                    val sections = uiState.sections
                    item {
                        FeaturedCarousel(
                            comics = sections.featured,
                            onComicClick = onComicClick,
                        )
                    }
                    item {
                        NewReleasesSection(
                            comics = sections.newReleases,
                            onComicClick = onComicClick,
                            onViewAll = onViewAllNewReleases,
                        )
                    }
                    item {
                        PopularGenresSection(
                            genres = sections.genres,
                            onGenreClick = onGenreClick,
                        )
                    }
                    item {
                        TopRatedSection(
                            comics = sections.topRated,
                            onComicClick = onComicClick,
                            onExplore = onExploreTopRated,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    comic: ComicUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ComicCard(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
        ) {
            ComicCover(
                url = comic.coverImageUrl,
                contentDescription = comic.title,
                modifier = Modifier.size(width = 48.dp, height = 64.dp),
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = comic.title.uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${comic.author} • ${comic.genre}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "★ ${comic.rating}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.extendedColors.rating,
                )
            }
            Text(
                text = comic.priceLabel,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Spacing.StackLg),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun EmptyState(searchQuery: String) {
    val message = if (searchQuery.isBlank()) {
        "No comics available yet."
    } else {
        "No results for \"$searchQuery\"."
    }
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Spacing.StackLg),
    )
}

@Composable
private fun ErrorState(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.Spacing.StackLg),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, heightDp = 1400, widthDp = 360)
@Composable
private fun HomeScreenPreview() {
    val sample = { id: Int, title: String, genre: String, author: String, rating: String, count: Int ->
        ComicUi(
            id = id, title = title, coverImageUrl = "", genre = genre, author = author,
            description = "One hero rises to reclaim the grid in a world of metal and madness.",
            rating = rating, ratingsCount = count, priceLabel = "$2.99",
        )
    }
    ComicBookRentalTheme {
        HomeScreen(
            uiState = HomeUiState.Content(
                sections = HomeData(
                    featured = listOf(sample(1, "Neon Reckoning", "Sci-Fi", "A. Vega", "4.9", 12000)),
                    newReleases = listOf(
                        sample(2, "Elemental 5", "Action", "K. Rogers", "4.5", 3000),
                        sample(3, "Tree of Aeons", "Fantasy", "M. Chen", "4.9", 5000),
                    ),
                    genres = listOf("Action", "Sci-Fi", "Noir", "Horror"),
                    topRated = listOf(
                        sample(4, "The Last Warrior", "Fantasy", "H. Miller", "5.0", 12000),
                        sample(5, "Cobalt Mystery", "Mystery", "S. Lane", "4.9", 8500),
                    ),
                ),
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onComicClick = {},
        )
    }
}
