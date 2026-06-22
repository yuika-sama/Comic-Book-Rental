package com.example.comicbookrental.data.repositories.rental

import com.example.comicbookrental.data.entities.RentalEntity
import kotlinx.coroutines.flow.Flow

interface RentalRepository {
    fun getAllRentals(): Flow<List<RentalEntity>>
    suspend fun insertRental(rental: RentalEntity)
}