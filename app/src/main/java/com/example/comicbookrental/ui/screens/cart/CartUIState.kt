package com.example.comicbookrental.ui.screens.cart

import com.example.comicbookrental.data.models.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Long = 0L
)