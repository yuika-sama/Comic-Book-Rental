package com.example.comicbookrental.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comics")
data class ComicEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val coverImageUrl: String,
    val genre: String,
    val author: String,
    val publisher: String,
    val description: String,
    val avgRating: String,
    val rentalPrice: String,
    val releaseDate: String
)
