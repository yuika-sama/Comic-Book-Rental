package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.entities.Comic


/** In-memory wishlist store (shared singleton, same approach as [MockCartData] / [MockRentalData]). */
object MockWishlistData {
    val comics = mutableListOf<Comic>()
}
