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

    fun loadRentals() {
        _uiState.value = _uiState.value.copy(
            rentalList = repository.getAllRentals()
        )
    }

    fun deleteRental(rentalId: Int) {
        repository.deleteRental(rentalId)
        loadRentals()
    }

    fun extendRental(rentalId: Int) {}
}