package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.data.entities.RentalStatus

object MockRentalData {

    private const val DAY = 24 * 60 * 60 * 1000L

    val rentals = mutableListOf(
        Rental(
            rentalId = 1,
            comicId = 1,
            userId = 1,
            comicTitle = "Solo Leveling",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAcXGr6r1HemXimBqcdQ2rIrAltN95YCLjwjQknzwnAA&s=10",
            rentalDate = System.currentTimeMillis() - DAY,
            dueDate = System.currentTimeMillis() + 6 * DAY,
            status = RentalStatus.ACTIVE
        ),
        Rental(
            rentalId = 3,
            comicId = 3,
            userId = 1,
            comicTitle = "Chainsaw Man",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFxxYZ8_Cbg5YN1uEo1-RfK1Uq16uNY4JJ3-i7bbqxqn4vZl-mpfC4VLg&s=10",
            rentalDate = System.currentTimeMillis() - DAY,
            dueDate = System.currentTimeMillis() + 6 * DAY,
            status = RentalStatus.ACTIVE
        ),
        Rental(
            rentalId = 5,
            comicId = 1312,
            userId = 1,
            comicTitle = "Bocchi the Rock",
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
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-g1PV_IrIiWZMhewfUosHJhLFjBx79XbJ0KSFQOxy-A&s",
            rentalDate = System.currentTimeMillis() - 10 * DAY,
            dueDate = System.currentTimeMillis() - DAY,
            status = RentalStatus.EXPIRED
        )
    )
}