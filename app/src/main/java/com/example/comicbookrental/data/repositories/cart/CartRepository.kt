package com.example.comicbookrental.data.repositories.cart

import com.example.comicbookrental.data.models.CartItem

interface CartRepository {

    fun getAllCartItems(): List<CartItem>

    fun addOrUpdateCartItem(item: CartItem)

    fun removeCartItem(comicId: Int)

    fun updateRentalDates(
        comicId: Int,
        startDate: Long,
        endDate: Long
    )

    fun clearCart()
}