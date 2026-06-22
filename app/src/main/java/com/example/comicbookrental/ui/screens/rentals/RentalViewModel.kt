package com.example.comicbookrental.ui.screens.rentals

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.entities.RentalEntity
import com.example.comicbookrental.data.repositories.rental.RentalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
    private val rentalRepository: RentalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RentalUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            rentalRepository.getAllRentals().collect { rentals ->
                _uiState.value = _uiState.value.copy(
                    rentalList = rentals
                )
            }
        }
    }

    fun insertTestRental() {
        viewModelScope.launch {
            rentalRepository.insertRental(
                RentalEntity(
                    comicId = 1,
                    userId = 1,
                    rentalDate = System.currentTimeMillis(),
                    dueDate = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000,
                    status = "ACTIVE"
                )
            )
        }
    }
}