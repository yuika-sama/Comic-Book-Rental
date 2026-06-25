package com.example.comicbookrental.data.repositories.checkout

import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.CheckoutSource
import com.example.comicbookrental.data.mock.MockCheckoutData

class CheckoutRepositoryImpl : CheckoutRepository {

    override fun getCheckoutItems(): List<CartItem> {
        return MockCheckoutData.checkoutItems.toList()
    }

    override fun getCheckoutSource(): CheckoutSource? {
        return MockCheckoutData.source
    }

    override fun prepareDirectCheckout(item: CartItem) {
        MockCheckoutData.source = CheckoutSource.DIRECT

        MockCheckoutData.checkoutItems.clear()
        MockCheckoutData.checkoutItems.add(item)
    }

    override fun prepareCartCheckout(items: List<CartItem>) {
        MockCheckoutData.source = CheckoutSource.CART

        MockCheckoutData.checkoutItems.clear()
        MockCheckoutData.checkoutItems.addAll(items)
    }

    override fun clearCheckout() {
        MockCheckoutData.source = null
        MockCheckoutData.checkoutItems.clear()
    }
}