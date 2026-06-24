package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.Rental

interface RentalRepository {

    fun getAllRentals(): List<Rental>

    fun insertRental(rental: Rental)

    fun deleteRental(rentalId: Int)

    fun getRentalById(rentalId: Int): Rental?

}