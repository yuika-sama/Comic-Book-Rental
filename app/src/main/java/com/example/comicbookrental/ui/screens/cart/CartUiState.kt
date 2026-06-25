package com.example.comicbookrental.ui.screens.cart

import com.example.comicbookrental.data.entities.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)