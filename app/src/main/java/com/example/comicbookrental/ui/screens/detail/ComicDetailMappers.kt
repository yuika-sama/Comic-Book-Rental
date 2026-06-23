package com.example.comicbookrental.ui.screens.detail

import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import com.example.comicbookrental.ui.components.ReviewUi
import com.example.comicbookrental.ui.components.SimilarTitleUi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale



private val reviewDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

private const val FULL_ARC_PRICE_MULTIPLIER = 8

private const val BONUS_NOTE = "Digital edition includes bonus art gallery."

fun ReviewEntity.toUi(): ReviewUi = ReviewUi(
    userName = userName,
    rating = rating,
    date = Instant.ofEpochMilli(commentDate)
        .atZone(ZoneId.systemDefault())
        .format(reviewDateFormatter),
    comment = comment,
)

fun ComicEntity.toSimilarUi(): SimilarTitleUi = SimilarTitleUi(
    id = id.toString(),
    title = title,
    price = "$$rentalPrice",
    coverUrl = coverImageUrl,
)

private fun buildRentOptions(rentalPrice: String): List<RentOptionUi> {
    val singlePrice = "$$rentalPrice"
    val arcPrice = rentalPrice.toDoubleOrNull()
        ?.let { "$%.2f".format(it * FULL_ARC_PRICE_MULTIPLIER) }
        ?: singlePrice
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

fun ComicEntity.toDetailUi(
    reviews: List<ReviewEntity>,
    similar: List<ComicEntity>,
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
