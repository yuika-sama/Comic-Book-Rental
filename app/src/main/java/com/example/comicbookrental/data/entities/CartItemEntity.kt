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

fun CartItem.rentalDays(): Long {
    return ((endDate - startDate) / MILLIS_PER_DAY)
        .coerceAtLeast(1L)
}

fun CartItem.totalPrice(): Long {
    return rentalDays() * pricePerDay
}