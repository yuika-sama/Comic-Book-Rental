package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

/**
 * A single rental plan in the Comic Detail "Rent Options" section: a hard-bordered card with the
 * plan [title] + [price] on one line, a small [subtitle] underneath, and a full-width CTA button.
 *
 * [highlighted] flips the card to the Action-Orange "featured plan" style (white CTA on orange);
 * the default is the paper card (ink CTA on cream). The CTA reuses [ComicButton].
 */
@Composable
fun RentOptionCard(
    title: String,
    price: String,
    subtitle: String,
    ctaText: String,
    onRent: () -> Unit,
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Md)
    val ink = MaterialTheme.extendedColors.ink

    val container = if (highlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val onContainer = if (highlighted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val priceColor = if (highlighted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    val subtitleColor = if (highlighted) {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = modifier
            .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink)
            .clip(shape)
            .background(container)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(Dimens.Spacing.StackMd),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.GridUnit)) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = onContainer,
                )
                Text(
                    text = subtitle.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = subtitleColor,
                )
            }
            Text(
                text = price,
                style = MaterialTheme.typography.headlineSmall,
                color = priceColor,
            )
        }

        ComicButton(
            text = ctaText,
            onClick = onRent,
            modifier = Modifier.fillMaxWidth(),
            containerColor = if (highlighted) MaterialTheme.colorScheme.surface else ink,
            contentColor = if (highlighted) ink else MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun RentOptionCardPreview() {
    ComicBookRentalTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.Margin),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            RentOptionCard(
                title = "Single Issue",
                price = "$1.99",
                subtitle = "7 Days Access",
                ctaText = "Rent Now",
                onRent = {},
            )
            RentOptionCard(
                title = "Full Arc (S1)",
                price = "$14.99",
                subtitle = "Permanent Library",
                ctaText = "Rent Season",
                onRent = {},
                highlighted = true,
            )
        }
    }
}
