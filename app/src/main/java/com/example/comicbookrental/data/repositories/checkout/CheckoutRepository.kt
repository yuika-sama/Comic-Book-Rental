package com.example.comicbookrental.data.repositories.checkout

import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.CheckoutSource


interface CheckoutRepository {

    fun getCheckoutItems(): List<CartItem>

    fun getCheckoutSource(): CheckoutSource?

    fun prepareDirectCheckout(item: CartItem)

    fun prepareCartCheckout(items: List<CartItem>)

    fun clearCheckout()


}