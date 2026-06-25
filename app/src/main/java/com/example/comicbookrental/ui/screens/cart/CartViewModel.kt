package com.example.comicbookrental.ui.screens.cart

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.MILLIS_PER_DAY
import com.example.comicbookrental.data.entities.totalPrice
import com.example.comicbookrental.domain.repository.CartRepository
import com.example.comicbookrental.data.repositories.cart.CartRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val MAX_RENTAL_DAYS = 30L

class CartViewModel : ViewModel() {

    private val repository: CartRepository = CartRepositoryImpl()

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        val items = repository.getAllCartItems()

        _uiState.value = _uiState.value.copy(
            cartItems = items,
            totalPrice = items.sumOf { item ->
                item.totalPrice()
            }
        )
    }

    fun refreshCart() {
        loadCart()
    }

    fun addComicToCart(
        comic: Comic,
        startDate: Long,
        endDate: Long
    ) {
        if (!isValidDateRange(startDate, endDate)) return

        val cartItem = CartItem(
            comicId = comic.id,
            comicTitle = comic.title,
            comicAuthor = comic.author,
            comicCoverUrl = comic.coverImageUrl,
            pricePerDay = comic.rentalPrice,
            startDate = startDate,
            endDate = endDate
        )

        repository.addOrUpdateCartItem(cartItem)

        loadCart()
    }

    fun removeFromCart(comicId: Int) {
        repository.removeCartItem(comicId)

        loadCart()
    }

    fun updateRentalDates(
        comicId: Int,
        startDate: Long,
        endDate: Long
    ) {
        if (!isValidDateRange(startDate, endDate)) return

        repository.updateRentalDates(
            comicId = comicId,
            startDate = startDate,
            endDate = endDate
        )

        loadCart()
    }

    fun clearCart() {
        repository.clearCart()

        loadCart()
    }

    private fun isValidDateRange(
        startDate: Long,
        endDate: Long
    ): Boolean {
        if (endDate <= startDate) return false

        val rentalDays = (endDate - startDate) / MILLIS_PER_DAY

        return rentalDays <= MAX_RENTAL_DAYS
    }


}