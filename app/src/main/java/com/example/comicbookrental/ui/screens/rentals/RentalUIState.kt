package com.example.comicbookrental.ui.screens.rentals

import com.example.comicbookrental.data.entities.RentalEntity

data class RentalUIState(
    val rentalList : List<RentalEntity> = emptyList(),
    val isLoading : Boolean = false
)