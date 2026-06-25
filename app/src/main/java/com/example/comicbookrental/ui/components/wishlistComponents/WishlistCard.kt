package com.example.comicbookrental.ui.components.wishlistComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.commonComponents.CartComicCover
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.TopBarIconButton
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

/**
 * Horizontal wishlist row: framed cover on the left, title/author + rating, then a RENT NOW CTA
 * and a filled-heart button to drop the title from the wishlist. Reuses [CartComicCover],
 * [ComicButton] and [TopBarIconButton]; stateless — the screen owns the data + callbacks.
 */
@Composable
fun WishlistCard(
    title: String,
    author: String,
    rating: String,
    coverUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onRent: () -> Unit = {},
    onRemove: () -> Unit = {},
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)
    val coverShape = RoundedCornerShape(Dimens.Radius.Inner)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .comicHardShadow(shape = shape, color = ink)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(Dimens.Border.Standard, ink, shape)
            .clickable(onClick = onClick)
            .padding(Dimens.Spacing.ContentSpacing),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        // Cover — framed thumbnail.
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(132.dp)
                .clip(coverShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(Dimens.Border.Standard, ink, coverShape),
        ) {
            CartComicCover(url = coverUrl, contentDescription = title)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .height(132.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title.uppercase(),
                        style = MaterialTheme.typography.titleSmall.copy(fontFamily = Anton),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = author,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                RatingBadge(rating = rating)
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ComicButton(
                    text = "Rent Now",
                    onClick = onRent,
                    modifier = Modifier.weight(1f),
                )
                TopBarIconButton(
                    icon = Icons.Filled.Favorite,
                    contentDescription = "Remove from wishlist",
                    onClick = onRemove,
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

/** Compact "★ rating" chip — ink-bordered, sits top-right of the card. */
@Composable
private fun RatingBadge(rating: String) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    Box(
        modifier = Modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(horizontal = Dimens.Spacing.StackSm, vertical = 2.dp),
    ) {
        Text(
            text = "★ $rating",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.extendedColors.rating,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun WishlistCardPreview() {
    ComicBookRentalTheme {
        Column(
            modifier = Modifier.padding(Dimens.Spacing.Margin),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            WishlistCard(
                title = "Neon Noir",
                author = "K. Murdock",
                rating = "5.0",
                coverUrl = "",
            )
            WishlistCard(
                title = "Ghost Runner #1",
                author = "Jax Vane",
                rating = "4.9",
                coverUrl = "",
            )
        }
    }
}
