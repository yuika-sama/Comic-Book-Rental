package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.RatingStars
import com.example.comicbookrental.ui.components.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun ReviewCard(
    userName: String,
    rating: Int,
    date: String,
    comment: String,
    modifier: Modifier = Modifier,
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InitialAvatar(name = userName)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.GridUnit),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                RatingStars(rating = rating.toFloat(), starSize = Dimens.Icon.Small)
            }
        }

        Text(
            text = comment,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/** Circular avatar showing the first letter of [name] — ink-bordered, Hero-Blue fill. */
@Composable
private fun InitialAvatar(name: String) {
    Box(
        modifier = Modifier
            .size(Dimens.Sizes.AvatarSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .border(Dimens.Border.Standard, MaterialTheme.extendedColors.ink, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name.trim().firstOrNull()?.uppercase() ?: "?",
            style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Anton),
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun ReviewCardPreview() {
    ComicBookRentalTheme {
        ReviewCard(
            userName = "Kenji R.",
            rating = 5,
            date = "12 Jun 2026",
            comment = "Absolutely electric. The Iron Shogun arc lands every punch and the art " +
                "in the mainframe sequence is unreal. Best issue of the run so far.",
            modifier = Modifier.padding(Dimens.Spacing.Margin),
        )
    }
}
