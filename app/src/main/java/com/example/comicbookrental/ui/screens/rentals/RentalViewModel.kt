package com.example.comicbookrental.ui.screens.rentals

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.domain.repository.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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