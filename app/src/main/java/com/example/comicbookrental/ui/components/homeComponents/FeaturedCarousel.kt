package com.example.comicbookrental.ui.components.homeComponents
import com.example.comicbookrental.ui.components.commonComponents.CartComicCover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.CartComicCover
import com.example.comicbookrental.ui.model.ComicUi
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens


@Composable
fun FeaturedCarousel(
    comics: List<ComicUi>,
    onComicClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 300.dp,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Dimens.Spacing.Margin),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
    ) {
        items(items = comics, key = { it.id }) { comic ->
            FeaturedComicCard(
                title = comic.title,
                description = comic.description,
                onClick = { onComicClick(comic.id) },
                cover = { CartComicCover(url = comic.coverImageUrl, contentDescription = comic.title) },
                modifier = Modifier.width(cardWidth),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun FeaturedCarouselPreview() {
    ComicBookRentalTheme {
        FeaturedCarousel(
            comics = listOf(
                ComicUi(
                    id = 1, title = "Neon Reckoning: Issue #1", coverImageUrl = "",
                    genre = "Sci-Fi", author = "A. Vega",
                    description = "In a world of metal and madness, one hero rises to reclaim the grid. Experience the saga.",
                    rating = "4.9", ratingsCount = 12000, priceLabel = "$3.49",
                ),
                ComicUi(
                    id = 2, title = "Starfall Saga", coverImageUrl = "",
                    genre = "Space Opera", author = "M. Cole",
                    description = "When the last star dims, a smuggler becomes the galaxy's only hope.",
                    rating = "4.7", ratingsCount = 9000, priceLabel = "$2.99",
                ),
            ),
            onComicClick = {},
        )
    }
}
