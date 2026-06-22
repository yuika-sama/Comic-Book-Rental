package com.example.comicbookrental.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens


@Composable
fun TopRatedSection(
    comics: List<ComicEntity>,
    onComicClick: (Int) -> Unit,
    onExplore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Spacing.Margin),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        SectionHeader(
            title = "Top Rated Epics",
            actionLabel = "Explore",
            onActionClick = onExplore,
            actionColor = MaterialTheme.colorScheme.secondary,
        )

        comics.forEachIndexed { index, comic ->
            RankedEpicCard(
                rank = index + 1,
                title = comic.title,
                author = comic.author,
                genre = comic.genre,
                rating = comic.avgRating,
                ratingsCount = comic.ratingsCount,
                accent = rankAccent(index),
                onClick = { onComicClick(comic.id) },
            )
        }
    }
}

/** #1 = Action Orange, #2 = Hero Blue, #3 = Tertiary, then cycle. */
@Composable
private fun rankAccent(index: Int): Color = when (index % 3) {
    0 -> MaterialTheme.colorScheme.primary
    1 -> MaterialTheme.colorScheme.secondary
    else -> MaterialTheme.colorScheme.tertiary
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun TopRatedSectionPreview() {
    ComicBookRentalTheme {
        TopRatedSection(
            comics = listOf(
                ComicEntity(
                    id = 1, title = "The Last Warrior", coverImageUrl = "", genre = "Fantasy",
                    author = "H. Miller", publisher = "Forge", description = "",
                    avgRating = "5.0", rentalPrice = "3.49", releaseDate = "2099-01-01",
                    ratingsCount = 12000,
                ),
                ComicEntity(
                    id = 2, title = "Cobalt Mystery", coverImageUrl = "", genre = "Mystery",
                    author = "S. Lane", publisher = "Noir House", description = "",
                    avgRating = "4.9", rentalPrice = "2.99", releaseDate = "2099-02-01",
                    ratingsCount = 8500,
                ),
            ),
            onComicClick = {},
            onExplore = {},
            modifier = Modifier.padding(vertical = Dimens.Spacing.Gutter),
        )
    }
}
