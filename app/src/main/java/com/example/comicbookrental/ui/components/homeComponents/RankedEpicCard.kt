package com.example.comicbookrental.ui.components.homeComponents
import com.example.comicbookrental.ui.components.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import kotlin.math.roundToInt


@Composable
fun RankedEpicCard(
    rank: Int,
    title: String,
    author: String,
    genre: String,
    rating: String,
    ratingsCount: Int,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    cover: @Composable BoxScope.() -> Unit = { CoverPlaceholder() },
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    Row(
        modifier = modifier
            .fillMaxWidth()
            .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .clickable(onClick = onClick)
            .padding(Dimens.Spacing.ContentSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        // Cover thumbnail.
        Box(
            modifier = Modifier
                .size(width = 64.dp, height = 88.dp)
                .clip(RoundedCornerShape(Dimens.Radius.Inner))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(Dimens.Border.Standard, ink, RoundedCornerShape(Dimens.Radius.Inner)),
        ) {
            cover()
        }

        // Title + meta + rating.
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm / 2),
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                color = accent,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "$author • $genre",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ListItemSpacing),
            ) {
                StarRow(rating = rating)
                Text(
                    text = "$rating (${formatCount(ratingsCount)} Ratings)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        // Rank badge.
        RankBadge(rank = rank, accent = accent)
    }
}

/** Square, hard-shadowed badge showing the leaderboard position (e.g. "#1"). */
@Composable
private fun RankBadge(rank: Int, accent: Color) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink
    Box(
        modifier = Modifier
            .size(44.dp)
            .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink)
            .clip(shape)
            .background(accent)
            .border(Dimens.Border.Standard, ink, shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "#$rank",
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = Anton),
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

/** Five stars filled up to the rounded [rating]; remainder dimmed. */
@Composable
private fun StarRow(rating: String) {
    val filled = (rating.toFloatOrNull() ?: 0f).roundToInt().coerceIn(0, 5)
    val gold = MaterialTheme.extendedColors.rating
    val dim = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.35f)
    Row {
        repeat(5) { i ->
            Text(
                text = "★",
                style = MaterialTheme.typography.bodySmall,
                color = if (i < filled) gold else dim,
            )
        }
    }
}

/** "12000" → "12k", small counts kept as-is. */
private fun formatCount(count: Int): String =
    if (count >= 1000) "${count / 1000}k" else count.toString()

/** Flat stand-in for the cover until an image-loading library is added. */
@Composable
private fun BoxScope.CoverPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun RankedEpicCardPreview() {
    ComicBookRentalTheme {
        RankedEpicCard(
            rank = 1,
            title = "The Last Warrior",
            author = "H. Miller",
            genre = "Fantasy",
            rating = "5.0",
            ratingsCount = 12000,
            accent = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(Dimens.Spacing.ScreenPadding),
        )
    }
}
