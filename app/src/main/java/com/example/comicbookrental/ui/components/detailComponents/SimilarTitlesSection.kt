package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.commonComponents.CartComicCover
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader

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
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens

data class SimilarTitleUi(
    val id: String,
    val title: String,
    val price: String,
    val coverUrl: String = "",
)


@Composable
fun SimilarTitlesSection(
    titles: List<SimilarTitleUi>,
    onViewAll: () -> Unit,
    onTitleClick: (SimilarTitleUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        SectionHeader(
            title = "Similar Titles",
            actionLabel = "View All",
            onActionClick = onViewAll,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
            contentPadding = PaddingValues(end = Dimens.Elevation.Resting),
        ) {
            items(items = titles, key = { it.id }) { item ->
                SimilarTitleCard(
                    title = item.title,
                    price = item.price,
                    onClick = { onTitleClick(item) },
                    modifier = Modifier.width(Dimens.Sizes.SimilarCardWidth),
                    cover = { CartComicCover(url = item.coverUrl, contentDescription = item.title) },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun SimilarTitlesSectionPreview() {
    ComicBookRentalTheme {
        SimilarTitlesSection(
            titles = listOf(
                SimilarTitleUi(id = "1", title = "Detective X: Ghost Protocol", price = "$1.50"),
                SimilarTitleUi(id = "2", title = "Mecha-Dragons #3", price = "$2.99"),
                SimilarTitleUi(id = "3", title = "Neon Samurai", price = "$0.99"),
            ),
            onViewAll = {},
            onTitleClick = {},
            modifier = Modifier.padding(Dimens.Spacing.Margin),
        )
    }
}
