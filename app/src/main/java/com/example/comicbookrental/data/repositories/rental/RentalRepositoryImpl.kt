package com.example.comicbookrental.data.repositories.rental

import com.example.comicbookrental.data.dao.RentalDao
import com.example.comicbookrental.data.entities.RentalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RentalRepositoryImpl @Inject constructor(
    private val rentalDao: RentalDao
) : RentalRepository {

    override fun getAllRentals(): Flow<List<RentalEntity>> {
        return rentalDao.getAllRentals()
    }

    override suspend fun insertRental(rental: RentalEntity) {
        rentalDao.insertRental(rental)
    }
}