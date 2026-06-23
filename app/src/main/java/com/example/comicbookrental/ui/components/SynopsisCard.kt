package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

/**
 * The Comic Detail "synopsis" panel: a hard-bordered, hard-shadowed paper card with a small
 * uppercase [label] header and the body [text]. The card itself is a solid surface; the
 * halftone dot texture belongs to the page behind it (see [halftoneBackground]).
 */
@Composable
fun SynopsisCard(
    text: String,
    modifier: Modifier = Modifier,
    label: String = "SYNOPSIS",
) {
    val shape = RoundedCornerShape(Dimens.Radius.Default)
    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = modifier
            .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(Dimens.Spacing.StackMd),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = ink,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun SynopsisCardPreview() {
    ComicBookRentalTheme {
        // Demonstrate the card on a halftone page background, as it sits in the screen.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .halftoneBackground()
                .padding(Dimens.Spacing.Margin),
        ) {
            SynopsisCard(
                text = "In the rain-slicked canyons of Neo-Tokyo, a forgotten ghost in the " +
                    "machine wakes up with a single directive: justice. Issue #42 takes us deep " +
                    "into the mainframe as Revenant faces the Iron Shogun's digital enforcers.",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
