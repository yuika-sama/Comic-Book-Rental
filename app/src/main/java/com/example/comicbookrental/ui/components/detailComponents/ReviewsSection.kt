package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.commonComponents.RatingStars
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens

data class ReviewUi(
    val userName: String,
    val rating: Int,
    val date: String,
    val comment: String,
)


@Composable
fun ReviewsSection(
    averageRating: Float,
    reviewCount: Int,
    reviews: List<ReviewUi>,
    modifier: Modifier = Modifier,
    collapsedCount: Int = DEFAULT_COLLAPSED_COUNT,
) {
    var expanded by remember(reviews) { mutableStateOf(false) }
    val canCollapse = reviews.size > collapsedCount
    val visibleReviews = if (expanded || !canCollapse) reviews else reviews.take(collapsedCount)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        SectionHeader(title = "Reviews & Ratings")

        RatingSummary(averageRating = averageRating, reviewCount = reviewCount)

        visibleReviews.forEach { review ->
            ReviewCard(
                userName = review.userName,
                rating = review.rating,
                date = review.date,
                comment = review.comment,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (canCollapse) {
            ComicButton(
                text = if (expanded) {
                    "Show less"
                } else {
                    "Show all %,d reviews".format(reviews.size)
                },
                onClick = { expanded = !expanded },
                variant = ComicButtonVariant.Secondary,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private const val DEFAULT_COLLAPSED_COUNT = 2

/** Big average score next to its star row and total count. */
@Composable
private fun RatingSummary(averageRating: Float, reviewCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        Text(
            text = "%.1f".format(averageRating),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.GridUnit)) {
            RatingStars(rating = averageRating, starSize = Dimens.Icon.Medium)
            Text(
                text = "BASED ON %,d REVIEWS".format(reviewCount),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun ReviewsSectionPreview() {
    ComicBookRentalTheme {
        ReviewsSection(
            averageRating = 4.8f,
            reviewCount = 1240,
            reviews = listOf(
                ReviewUi(
                    userName = "Kenji R.",
                    rating = 5,
                    date = "12 Jun 2026",
                    comment = "Absolutely electric. The Iron Shogun arc lands every punch and the " +
                        "art in the mainframe sequence is unreal.",
                ),
                ReviewUi(
                    userName = "Mara V.",
                    rating = 4,
                    date = "03 Jun 2026",
                    comment = "Great pacing, though the cliffhanger felt a touch rushed. Still " +
                        "can't wait for #43.",
                ),
                ReviewUi(
                    userName = "Theo P.",
                    rating = 5,
                    date = "28 May 2026",
                    comment = "Hidden behind the 'Show all' button — proof the section expands.",
                ),
            ),
            modifier = Modifier.padding(Dimens.Spacing.Margin),
        )
    }
}
