package com.example.comicbookrental.ui.screens.detail

import com.example.comicbookrental.data.models.Comic
import com.example.comicbookrental.data.models.Review
import com.example.comicbookrental.ui.components.detailComponents.ReviewUi
import com.example.comicbookrental.ui.components.detailComponents.SimilarTitleUi
import com.example.comicbookrental.ui.utils.toPriceLabel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale



private val reviewDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

private const val FULL_ARC_PRICE_MULTIPLIER = 8

private const val BONUS_NOTE = "Digital edition includes bonus art gallery."

fun Review.toUi(): ReviewUi = ReviewUi(
    userName = userName,
    rating = rating,
    date = Instant.ofEpochMilli(commentDate)
        .atZone(ZoneId.systemDefault())
        .format(reviewDateFormatter),
    comment = comment,
)

fun Comic.toSimilarUi(): SimilarTitleUi = SimilarTitleUi(
    id = id.toString(),
    title = title,
    price = rentalPrice.toPriceLabel(),
    coverUrl = coverImageUrl,
)

private fun buildRentOptions(rentalPrice: Double): List<RentOptionUi> {
    val singlePrice = rentalPrice.toPriceLabel()
    val arcPrice = (rentalPrice * FULL_ARC_PRICE_MULTIPLIER).toPriceLabel()
    return listOf(
        RentOptionUi(
            id = "single",
            title = "Single Issue",
            price = singlePrice,
            subtitle = "7 Days Access",
            ctaText = "Rent Now",
        ),
        RentOptionUi(
            id = "full_arc",
            title = "Full Arc (S1)",
            price = arcPrice,
            subtitle = "Permanent Library",
            ctaText = "Rent Season",
            highlighted = true,
        ),
    )
}

fun Comic.toDetailUi(
    reviews: List<Review>,
    similar: List<Comic>,
): ComicDetailUi = ComicDetailUi(
    title = title,
    creators = author,
    publisher = publisher,
    genres = listOf(genre),
    rating = avgRating.toFloatOrNull() ?: 0f,
    reviewCount = ratingsCount,
    coverUrl = coverImageUrl,
    badgeText = if (isFeatured) "HOT PICK" else null,
    synopsis = description,
    rentOptions = buildRentOptions(rentalPrice),
    bonusNote = BONUS_NOTE,
    reviews = reviews.map { it.toUi() },
    similarTitles = similar.map { it.toSimilarUi() },
)
