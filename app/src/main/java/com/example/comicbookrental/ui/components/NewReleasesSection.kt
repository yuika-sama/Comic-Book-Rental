package com.example.comicbookrental.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens


@Composable
fun NewReleasesSection(
    comics: List<ComicEntity>,
    onComicClick: (Int) -> Unit,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "New Releases",
    cardWidth: Dp = 150.dp,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        SectionHeader(
            title = title,
            actionLabel = "View All",
            onActionClick = onViewAll,
            modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = Dimens.Spacing.Margin),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
        ) {
            items(items = comics, key = { it.id }) { comic ->
                NewReleaseCard(
                    title = comic.title,
                    author = comic.author,
                    rating = comic.avgRating,
                    onClick = { onComicClick(comic.id) },
                    modifier = Modifier.width(cardWidth),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun NewReleasesSectionPreview() {
    ComicBookRentalTheme {
        NewReleasesSection(
            comics = listOf(
                ComicEntity(
                    id = 1, title = "Elemental 5", coverImageUrl = "", genre = "Action",
                    author = "K. Rogers", publisher = "Bolt", description = "",
                    avgRating = "4.5", rentalPrice = "2.99", releaseDate = "2099-01-01",
                ),
                ComicEntity(
                    id = 2, title = "Tree of Aeons", coverImageUrl = "", genre = "Fantasy",
                    author = "M. Chen", publisher = "Root", description = "",
                    avgRating = "4.9", rentalPrice = "3.49", releaseDate = "2099-02-01",
                ),
            ),
            onComicClick = {},
            onViewAll = {},
            modifier = Modifier.padding(vertical = Dimens.Spacing.Gutter),
        )
    }
}
