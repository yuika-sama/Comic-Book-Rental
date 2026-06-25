package com.example.comicbookrental.data.repositories.wishlist

import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.data.mock.MockWishlistData
import com.example.comicbookrental.domain.repository.WishlistRepository

class WishlistRepositoryImpl : WishlistRepository
{

    override fun getAll(): List<Comic> {
        return MockWishlistData.comics.toList()
    }

    override fun isInWishlist(comicId: Int): Boolean {
        return MockWishlistData.comics.any { it.id == comicId }
    }

    override fun toggle(comic: Comic): Boolean {
        return if (isInWishlist(comic.id)) {
            remove(comic.id)
            false
        } else {
            MockWishlistData.comics.add(comic)
            true
        }
    }

    override fun remove(comicId: Int) {
        MockWishlistData.comics.removeAll { it.id == comicId }
    }
}
