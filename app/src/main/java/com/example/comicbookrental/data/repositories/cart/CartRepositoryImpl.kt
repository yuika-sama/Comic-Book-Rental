package com.example.comicbookrental.data.repositories.cart

import com.example.comicbookrental.data.mock.MockCartData
import com.example.comicbookrental.data.models.CartItem

class CartRepositoryImpl : CartRepository {

    override fun getAllCartItems(): List<CartItem> {
        // Không trả list gốc ra ngoài.
        return MockCartData.cartItems.toList()
    }

    override fun addOrUpdateCartItem(item: CartItem) {
        val existingIndex = MockCartData.cartItems.indexOfFirst {
            it.comicId == item.comicId
        }

        if (existingIndex == -1) {
            // Chưa có comic này trong cart.
            MockCartData.cartItems.add(item)
        } else {
            // Đã có rồi thì update ngày thuê, không tạo item trùng.
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