package com.example.comicbookrental.data.dto

import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import kotlinx.serialization.Serializable

@Serializable
data class ComicDto(
    val id: Int,
    val title: String,
    val coverImageUrl: String,
    val genre: String,
    val author: String,
    val publisher: String,
    val description: String,
    val avgRating: String,
    val rentalPrice: String,
    val releaseDate: String,
    val viewCount: Int = 0,
    val ratingsCount: Int = 0,
    val isFeatured: Boolean = false,
    val reviews: List<ReviewDto> = emptyList()
)

@Serializable
data class ReviewDto(
    val userName: String,
    val rating: Int,
    val comment: String,
    val commentDate: Long,
)

fun ComicDto.toEntity(): ComicEntity = ComicEntity(
    id = id,
    title = title,
    coverImageUrl = coverImageUrl,
    genre = genre,
    author = author,
    publisher = publisher,
    description = description,
    avgRating = avgRating,
    rentalPrice = rentalPrice,
    releaseDate = releaseDate,
    viewCount = viewCount,
    ratingsCount = ratingsCount,
    isFeatured = isFeatured,
)


fun ComicDto.toReviewEntities(): List<ReviewEntity> = reviews.map { r ->
    ReviewEntity(
        comicId = id,
        userName = r.userName,
        rating = r.rating,
        comment = r.comment,
        commentDate = r.commentDate,
    )
}