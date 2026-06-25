package com.example.comicbookrental.ui.screens.rentals

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.domain.repository.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
private const val DAY_IN_MILLIS = 24L * 60 * 60 * 1000L

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

    fun createExtensionCheckoutItem(
        rental: Rental,
        extraDays: Int
    ): CartItem {
        val now = System.currentTimeMillis()


        val startDate = maxOf(
            rental.dueDate,
            now
        )

        val endDate = startDate +
                extraDays.toLong() * DAY_IN_MILLIS

        return CartItem(
            comicId = rental.comicId,
            comicTitle = "${rental.comicTitle} - Extension",
            comicAuthor = "",
            comicCoverUrl = rental.comicCoverUrl,
            pricePerDay = rental.pricePerDay,
            startDate = startDate,
            endDate = endDate,

            extensionRentalId = rental.rentalId
        )
    }
}