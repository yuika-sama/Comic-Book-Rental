package com.example.comicbookrental.data.repositories.rental

import android.os.storage.StorageManager
import com.example.comicbookrental.data.mock.MockRentalData
import com.example.comicbookrental.data.entities.Rental
import javax.inject.Inject

class RentalRepositoryImpl : RentalRepository {

    override fun getAllRentals(): List<Rental> {
        return MockRentalData.rentals.toList()
    }

    override fun insertRental(rental: Rental) {
        MockRentalData.rentals.add(rental)
    }

    override fun deleteRental(rentalId: Int) {
        MockRentalData.rentals.removeIf {
            it.rentalId == rentalId
        }
    }

    override fun getRentalById(rentalId: Int): Rental? {
        return MockRentalData.rentals.find {
            it.rentalId == rentalId
        }
    }
}