package com.example.comicbookrental.ui.components.homeComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.comicbookrental.ui.components.commonComponents.rememberComicPressState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun NewReleaseCard(
    title: String,
    author: String,
    rating: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    coverHeight: Dp = 160.dp,
    cover: @Composable BoxScope.() -> Unit = { CoverPlaceholder() },
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    val interactionSource = remember { MutableInteractionSource() }
    val press = rememberComicPressState(interactionSource)

    // Two separate framed rectangles stacked with a gap: a shadowed cover on top, a flat
    // (shadowless) text panel below.
    Column(
        modifier = modifier
            .offset(x = press.translation, y = press.translation)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
    ) {
        // 1) Cover art — its own frame with a hard shadow.
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
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = author,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "★ $rating",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.extendedColors.rating,
            )
        }
    }
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
private fun NewReleaseCardPreview() {
    ComicBookRentalTheme {
        NewReleaseCard(
            title = "Elemental 5",
            author = "K. Rogers",
            rating = "4.5",
            modifier = Modifier
                .padding(Dimens.Spacing.ScreenPadding)
                .width(150.dp),
        )
    }
}
