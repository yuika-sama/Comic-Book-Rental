package com.example.comicbookrental.data.repositories.cart

import com.example.comicbookrental.data.mock.MockCartData
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.utils.StoreManager
import javax.inject.Inject

class CartRepositoryImpl : CartRepository {

    override fun getAllCartItems(): List<CartItem> {
        return MockCartData.cartItems.toList()
    }

    override fun addOrUpdateCartItem(item: CartItem) {
        val existingIndex = MockCartData.cartItems.indexOfFirst {
            it.comicId == item.comicId
        }

        if (existingIndex == -1) {
            MockCartData.cartItems.add(item)
        } else {
            MockCartData.cartItems[existingIndex] = item
        }
    }

    override fun removeCartItem(comicId: Int) {
        MockCartData.cartItems.removeAll { item ->
            item.comicId == comicId
        }
    }

    override fun updateRentalDates(
        comicId: Int,
        startDate: Long,
        endDate: Long
    ) {
        val index = MockCartData.cartItems.indexOfFirst {
            it.comicId == comicId
        }

        if (index != -1) {
            val oldItem = MockCartData.cartItems[index]

            MockCartData.cartItems[index] = oldItem.copy(
                startDate = startDate,
                endDate = endDate
            )
        }
    }

    override fun clearCart() {
        MockCartData.cartItems.clear()
    }
}