package com.example.comicbookrental.data.repositories.wishlist

import com.example.comicbookrental.data.entities.Comic


interface WishlistRepository {

    fun getAll(): List<Comic>

    fun isInWishlist(comicId: Int): Boolean

    /** Adds or removes [comic]; returns the new favorite state (true = now in wishlist). */
    fun toggle(comic: Comic): Boolean

    fun remove(comicId: Int)
}
