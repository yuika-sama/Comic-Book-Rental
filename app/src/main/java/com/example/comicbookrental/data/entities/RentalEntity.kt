package com.example.comicbookrental.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rentals")
data class RentalEntity(
    @PrimaryKey(autoGenerate = true)
    val rentalId: Int = 0,
    val comicId: Int,
    val userId: Int,
    val rentalDate: Long,
    val dueDate: Long,
    val status: String
)