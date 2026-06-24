package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.RatingStars
import com.example.comicbookrental.ui.components.GenreCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

/**
 * The Comic Detail metadata header that sits under the hero cover: a row of genre tags, the
 * issue title, the creator credit, the publisher, and a star-rating row.
 *
 * Reuses [GenreCard] (in its compact form) for the tags and [RatingStars] for the rating.
 */
@Composable
fun ComicTitleBlock(
    title: String,
    creators: String,
    publisher: String,
    genres: List<String>,
    rating: Float,
    reviewCount: Int,
    modifier: Modifier = Modifier,
) {
    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm)) {
            genres.forEach { genre ->
                GenreCard(
                    label = genre,
                    accent = ink,
                    filled = true,
                    height = Dimens.Sizes.ChipHeight,
                    textStyle = MaterialTheme.typography.labelLarge.copy(fontFamily = Anton),
                    horizontalPadding = Dimens.Spacing.ContentSpacing,
                )
            }
        }

        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "By $creators",
            style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = publisher.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
        ) {
            RatingStars(rating = rating, starSize = Dimens.Icon.Medium)
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append(rating.toString())
                    }
                    append("  (%,d REVIEWS)".format(reviewCount))
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun ComicTitleBlockPreview() {
    ComicBookRentalTheme {
        ComicTitleBlock(
            title = "Cyber-Soul Revenant #42",
            creators = "J.J. Inkwell & The Neon Collective",
            publisher = "Titan Graphics Publishing",
            genres = listOf("Sci-Fi", "Noir"),
            rating = 4.5f,
            reviewCount = 1240,
            modifier = Modifier.padding(Dimens.Spacing.Margin),
        )
    }
}
