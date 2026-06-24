package com.example.comicbookrental.ui.components.homeComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun FeaturedComicCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    coverHeight: Dp = 260.dp,
    shadowColor: Color = MaterialTheme.colorScheme.primary,
    cover: @Composable BoxScope.() -> Unit = { CoverPlaceholder() },
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = modifier
            .comicHardShadow(shape = shape, offset = Dimens.Elevation.Raised, color = shadowColor)
            .clip(shape)
            .background(ink)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight),
        ) {
            cover()
            FeaturedBadge(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(Dimens.Spacing.ContentSpacing),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.ContentSpacing),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ListItemSpacing),
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun FeaturedBadge(modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    Text(
        text = "FEATURED STORY",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.primary)
            .border(Dimens.Border.Hairline, MaterialTheme.extendedColors.ink, shape)
            .padding(horizontal = Dimens.Spacing.ListItemSpacing, vertical = 4.dp),
    )
}

/** Flat stand-in for the cover until an image-loading library is added. */
@Composable
private fun BoxScope.CoverPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun FeaturedComicCardPreview() {
    ComicBookRentalTheme {
        FeaturedComicCard(
            title = "Neon Reckoning: Issue #1",
            description = "In a world of metal and madness, one hero rises to reclaim the grid. Experience the saga.",
            modifier = Modifier
                .padding(Dimens.Spacing.ScreenPadding)
                .width(300.dp),
        )
    }
}
