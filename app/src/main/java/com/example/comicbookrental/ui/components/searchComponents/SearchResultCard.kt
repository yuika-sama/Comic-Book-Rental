package com.example.comicbookrental.ui.components.searchComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.comicbookrental.ui.components.commonComponents.rememberComicPressState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

/**
 * Comic card used in the Search screen's results grid. Same "two framed panels" structure as
 * [NewReleaseCard] (a shadowed cover above a flat text panel), but the footer leads with a
 * color-coded genre eyebrow and shows either the rating or a "HOT" indicator for popular titles.
 */
@Composable
fun SearchResultCard(
    title: String,
    genre: String,
    rating: String,
    modifier: Modifier = Modifier,
    isHot: Boolean = false,
    onClick: () -> Unit = {},
    coverHeight: Dp = 200.dp,
    cover: @Composable BoxScope.() -> Unit = {},
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    val interactionSource = remember { MutableInteractionSource() }
    val press = rememberComicPressState(interactionSource)

    Column(
        modifier = modifier
            .offset(x = press.translation, y = press.translation)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
    ) {
        // 1) Cover art — framed with a hard shadow.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight)
                .comicHardShadow(shape = shape, offset = press.shadowOffset, color = ink)
                .clip(shape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(width = Dimens.Border.Standard, color = ink, shape = shape),
        ) {
            cover()
        }

        Spacer(Modifier.height(Dimens.Spacing.ListItemSpacing))

        // 2) Text panel — its own frame, NO shadow.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(MaterialTheme.colorScheme.surface)
                .border(width = Dimens.Border.Standard, color = ink, shape = shape)
                .padding(Dimens.Spacing.ListItemSpacing),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = genre.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (isHot) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalFireDepartment,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimens.Icon.Small),
                    )
                    Text(
                        text = "HOT",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            } else {
                Text(
                    text = "★ $rating",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.extendedColors.rating,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun SearchResultCardPreview() {
    ComicBookRentalTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
            modifier = Modifier.padding(Dimens.Spacing.ScreenPadding),
        ) {
            SearchResultCard(
                title = "Neon Drifter: Vol 1",
                genre = "Sci-Fi",
                rating = "4.9",
                modifier = Modifier.width(150.dp),
            )
            SearchResultCard(
                title = "Shadows of Sector 7",
                genre = "Noir",
                rating = "4.5",
                isHot = true,
                modifier = Modifier.width(150.dp),
            )
        }
    }
}
