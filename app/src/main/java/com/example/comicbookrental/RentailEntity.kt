package com.example.comicbookrental

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

    val status: RentalStatus
)

enum class RentalStatus {
    ACTIVE,
    EXPIRED
}