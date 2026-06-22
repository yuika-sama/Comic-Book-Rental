package com.example.comicbookrental.ui.screens.rentals

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.data.models.Rental
import com.example.comicbookrental.data.models.RentalStatus
import com.example.comicbookrental.data.repositories.rental.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class RentalViewModel : ViewModel() {

    private val repository: RentalRepository =
        RentalRepositoryImpl()

    private val _uiState =
        MutableStateFlow(RentalUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadRentals()
    }

    private fun loadRentals() {
        _uiState.value = _uiState.value.copy(
            rentalList = repository.getAllRentals()
        )
    }

    fun insertTestRental() {
        val now = System.currentTimeMillis()
        val sevenDays = 7 * 24 * 60 * 60 * 1000L

        repository.insertRental(
            Rental(
                rentalId = Random.nextInt(1000, 9999),
                comicId = 999,
                userId = 1,
                comicTitle = "Chainsaw Man",
                comicCoverUrl = "",
                rentalDate = now,
                dueDate = now + sevenDays,
                status = RentalStatus.ACTIVE
            )
        )

        loadRentals()
    }

    fun deleteRental(rentalId: Int) {
        repository.deleteRental(rentalId)
        loadRentals()
    }
}