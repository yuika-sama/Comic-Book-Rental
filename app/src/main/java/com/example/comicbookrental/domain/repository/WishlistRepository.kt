package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.Comic


interface WishlistRepository {

    fun getAll(): List<Comic>

    fun isInWishlist(comicId: Int): Boolean

    fun toggle(comic: Comic): Boolean

    fun remove(comicId: Int)
}
