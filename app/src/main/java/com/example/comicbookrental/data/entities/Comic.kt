package com.example.comicbookrental.data.entities

import kotlinx.serialization.Serializable


@Serializable
data class Comic(
    val id: Int,
    val title: String,
    val coverImageUrl: String,
    val genre: String,
    val author: String,
    val publisher: String,
    val description: String,
    val avgRating: String,
    val rentalPrice: Double,
    val releaseDate: String,
    val viewCount: Int = 0,
    val ratingsCount: Int = 0,
    val isFeatured: Boolean = false,
    val reviews: List<Review> = emptyList(),
)

@Serializable
data class Review(
    val userName: String,
    val rating: Int,
    val comment: String,
    val commentDate: Long,
)
