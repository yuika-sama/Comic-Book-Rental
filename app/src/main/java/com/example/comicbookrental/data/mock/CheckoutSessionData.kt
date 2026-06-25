package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.CheckoutSource

object CheckoutSessionData {
    var source: CheckoutSource? = null

    val checkoutItems = mutableListOf<CartItem>()
}