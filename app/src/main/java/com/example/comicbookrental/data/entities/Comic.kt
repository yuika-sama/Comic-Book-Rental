package com.example.comicbookrental.data.entities

import kotlinx.serialization.Serializable


@Serializable
data class Comic(
    val id: Int,
    val title: String,
    val coverImageUrl: String,
    val genres: List<String> = emptyList(),
    val author: String,
    val publisher: String,
    val description: String,
    val avgRating: String,
    val rentalPrice: Double,
    val releaseDate: String,
    val viewCount: Int = 0,
    val ratingsCount: Int = 0,
    val isFeatured: Boolean = false,
    val latestChapter: Int = 1,
    val reviews: List<Review> = emptyList(),
) {
    val genreLabel: String get() = genres.joinToString(", ")
}

@Serializable
data class Review(
    val userName: String,
    val rating: Int,
    val comment: String,
    val commentDate: Long,
)
