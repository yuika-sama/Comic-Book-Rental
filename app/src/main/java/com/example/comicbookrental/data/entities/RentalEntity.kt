package com.example.comicbookrental.data.entities

data class Rental(

    val rentalId: Int,

    val comicId: Int,

    val userId: Int,

    val comicTitle: String,

    val comicCoverUrl: String,

    val rentalDate: Long,

    val dueDate: Long,

    val status: RentalStatus
)
enum class RentalStatus {
    ACTIVE,
    EXPIRED
}