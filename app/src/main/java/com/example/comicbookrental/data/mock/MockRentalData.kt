package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.models.Rental
import com.example.comicbookrental.data.models.RentalStatus

object MockRentalData {

    private const val DAY = 24 * 60 * 60 * 1000L

    val rentals = mutableListOf(
        Rental(
            rentalId = 1,
            comicId = 101,
            userId = 1,
            comicTitle = "Solo Leveling",
            comicCoverUrl = "",
            rentalDate = System.currentTimeMillis() - DAY,
            dueDate = System.currentTimeMillis() + 6 * DAY,
            status = RentalStatus.ACTIVE
        ),
        Rental(
            rentalId = 2,
            comicId = 102,
            userId = 1,
            comicTitle = "One Piece",
            comicCoverUrl = "",
            rentalDate = System.currentTimeMillis() - 10 * DAY,
            dueDate = System.currentTimeMillis() - DAY,
            status = RentalStatus.EXPIRED
        )
    )
}