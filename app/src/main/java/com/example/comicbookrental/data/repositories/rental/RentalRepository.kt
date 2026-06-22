package com.example.comicbookrental.data.repositories.rental

import com.example.comicbookrental.data.models.Rental

interface RentalRepository {

    fun getAllRentals(): List<Rental>

    fun insertRental(rental: Rental)

    fun deleteRental(rentalId: Int)

    fun getRentalById(rentalId: Int): Rental?
}