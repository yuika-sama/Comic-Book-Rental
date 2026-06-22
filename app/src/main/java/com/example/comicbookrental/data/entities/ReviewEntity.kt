package com.example.comicbookrental.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reviews",
    foreignKeys = [ForeignKey(
        entity = ComicEntity::class,
        parentColumns = ["id"],
        childColumns = ["comicId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("comicId")]
)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val comicId: Int,
    val userName: String,
    val rating: Int,
    val comment: String,
    val commentDate: Long
)