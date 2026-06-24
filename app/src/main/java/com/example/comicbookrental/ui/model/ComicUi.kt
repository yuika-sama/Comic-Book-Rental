package com.example.comicbookrental.ui.model

import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.ui.utils.toPriceLabel


data class ComicUi(
    val id: Int,
    val title: String,
    val coverImageUrl: String,
    val genre: String,
    val author: String,
    val description: String,
    val rating: String,
    val ratingsCount: Int,
    val priceLabel: String,
)

fun ComicEntity.toUi(): ComicUi = ComicUi(
    id = id,
    title = title,
    coverImageUrl = coverImageUrl,
    genre = genre,
    author = author,
    description = description,
    rating = avgRating,
    ratingsCount = ratingsCount,
    priceLabel = rentalPrice.toPriceLabel(),
)
