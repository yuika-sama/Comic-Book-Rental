package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
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


@Composable
fun HazardBanner(
    message: String,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Default)
    val ink = MaterialTheme.extendedColors.ink

    Row(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .hazardStripes(stripeColor = MaterialTheme.colorScheme.primary)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(horizontal = Dimens.Spacing.StackMd, vertical = Dimens.Spacing.ContentSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            tint = ink,
            modifier = Modifier.size(Dimens.Icon.Medium),
        )
        Text(
            text = message.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = ink,
            modifier = Modifier.weight(1f),
        )
        if (onDismiss != null) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Dismiss",
                tint = ink,
                modifier = Modifier
                    .clickable(onClick = onDismiss)
                    .size(Dimens.Icon.Medium),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun HazardBannerPreview() {
    ComicBookRentalTheme {
        HazardBanner(
            message = "Couldn't load comics",
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.Margin),
        )
    }
}
