package com.example.comicbookrental.data.entities

import kotlinx.serialization.Serializable

const val MILLIS_PER_DAY = 24L * 60L * 60L * 1000L

@Serializable
data class CartItem(
    val comicId: Int,
    val comicTitle: String,
    val comicAuthor: String,
    val comicCoverUrl: String,

    val pricePerDay: Long,

    val startDate: Long,
    val endDate: Long
)

/** Builds a [CartItem] for [this] comic over the chosen rental window. Shared by the
 *  "Add to cart" and "Rent Now" flows so both produce identical cart entries. */
fun Comic.toCartItem(startDate: Long, endDate: Long): CartItem {
    return CartItem(
        comicId = id,
        comicTitle = title,
        comicAuthor = author,
        comicCoverUrl = coverImageUrl,
        pricePerDay = rentalPrice.toLong(),
        startDate = startDate,
        endDate = endDate,
    )
}

fun CartItem.rentalDays(): Long {
    return ((endDate - startDate) / MILLIS_PER_DAY)
        .coerceAtLeast(1L)
}

fun CartItem.totalPrice(): Long {
    return rentalDays() * pricePerDay
}