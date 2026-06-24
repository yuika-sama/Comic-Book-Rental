package com.example.comicbookrental.ui.components.homeComponents
import com.example.comicbookrental.ui.components.commonComponents.CartComicCover
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.components.CartComicCover
import com.example.comicbookrental.ui.model.ComicUi
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens


@Composable
fun TopRatedSection(
    comics: List<ComicUi>,
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
                rating = comic.rating,
                ratingsCount = comic.ratingsCount,
                accent = rankAccent(index),
                cover = { CartComicCover(url = comic.coverImageUrl, contentDescription = comic.title) },
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
                ComicUi(
                    id = 1, title = "The Last Warrior", coverImageUrl = "", genre = "Fantasy",
                    author = "H. Miller", description = "",
                    rating = "5.0", ratingsCount = 12000, priceLabel = "$3.49",
                ),
                ComicUi(
                    id = 2, title = "Cobalt Mystery", coverImageUrl = "", genre = "Mystery",
                    author = "S. Lane", description = "",
                    rating = "4.9", ratingsCount = 8500, priceLabel = "$2.99",
                ),
            ),
            onComicClick = {},
            onExplore = {},
            modifier = Modifier.padding(vertical = Dimens.Spacing.Gutter),
        )
    }
}
