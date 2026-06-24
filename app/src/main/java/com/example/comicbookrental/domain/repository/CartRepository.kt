package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.CartItem

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