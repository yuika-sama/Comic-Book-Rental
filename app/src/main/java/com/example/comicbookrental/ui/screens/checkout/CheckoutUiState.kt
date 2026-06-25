package com.example.comicbookrental.ui.screens.checkout

import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.CheckoutSource
import com.example.comicbookrental.data.entities.PaymentMethod


data class CheckoutUiState(
    val checkoutItems: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,

    val source: CheckoutSource? = null,
    val paymentMethod: PaymentMethod = PaymentMethod.CREDIT_CARD,

    val isCompleted: Boolean = false,
    val receiptId: String = "",
    val completedItems: List<CartItem> = emptyList()
)