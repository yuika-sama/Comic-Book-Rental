package com.example.comicbookrental.ui.screens.checkout

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.CheckoutSource
import com.example.comicbookrental.data.entities.PaymentMethod
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.data.entities.RentalStatus
import com.example.comicbookrental.data.entities.totalPrice
import com.example.comicbookrental.domain.repository.CartRepository
import com.example.comicbookrental.domain.repository.RentalRepository
import com.example.comicbookrental.data.repositories.cart.CartRepositoryImpl
import com.example.comicbookrental.data.repositories.checkout.CheckoutRepository
import com.example.comicbookrental.data.repositories.checkout.CheckoutRepositoryImpl
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutViewModel : ViewModel() {

    private val cartRepository: CartRepository =
        CartRepositoryImpl()

    private val checkoutRepository: CheckoutRepository =
        CheckoutRepositoryImpl()

    private val rentalRepository: RentalRepository =
        RentalRepositoryImpl()

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPreparedCheckout()
    }

    private fun loadPreparedCheckout() {
        updateCheckout(
            items = checkoutRepository.getCheckoutItems(),
            source = checkoutRepository.getCheckoutSource()
        )
    }

    // DetailScreen: Rent Now
    fun startDirectCheckout(item: CartItem) {
        updateCheckout(
            items = listOf(item),
            source = CheckoutSource.DIRECT
        )
    }

    // CartScreen: Proceed To Checkout
    fun startCartCheckout() {
        val cartItems = cartRepository.getAllCartItems()

        updateCheckout(
            items = cartItems,
            source = CheckoutSource.CART
        )
    }

    private fun updateCheckout(
        items: List<CartItem>,
        source: CheckoutSource?
    ) {
        _uiState.value = CheckoutUiState(
            checkoutItems = items,
            totalPrice = items.sumOf { it.totalPrice() },
            source = source,
            paymentMethod = PaymentMethod.CREDIT_CARD
        )
    }

    fun selectPaymentMethod(paymentMethod: PaymentMethod) {
        _uiState.value = _uiState.value.copy(
            paymentMethod = paymentMethod
        )
    }

    fun confirmCheckout() {
        val state = _uiState.value

        if (state.checkoutItems.isEmpty()) return

        var nextRentalId = rentalRepository.getNextRentalId()

        state.checkoutItems.forEach { item ->
            rentalRepository.insertRental(
                Rental(
                    rentalId = nextRentalId++,
                    comicId = item.comicId,
                    userId = 1,
                    comicTitle = item.comicTitle,
                    comicCoverUrl = item.comicCoverUrl,
                    rentalDate = item.startDate,
                    dueDate = item.endDate,
                    status = RentalStatus.ACTIVE
                )
            )
        }

        // Chỉ clear cart nếu checkout đi từ CartScreen.
        if (state.source == CheckoutSource.CART) {
            cartRepository.clearCart()
        }

        _uiState.value = state.copy(
            isCompleted = true,
            receiptId = "RENT-${System.currentTimeMillis()}",
            completedItems = state.checkoutItems
        )
    }

    fun resetCheckout() {
        checkoutRepository.clearCheckout()
        _uiState.value = CheckoutUiState()
    }
}
