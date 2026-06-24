package com.example.comicbookrental.ui.screens.rentals

import com.example.comicbookrental.data.entities.Rental

data class RentalUiState(
    val rentalList: List<Rental> = emptyList(),
    val isLoading: Boolean = false
)