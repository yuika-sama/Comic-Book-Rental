package com.example.comicbookrental.ui.screens.wishlist

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.data.repositories.wishlist.WishlistRepository
import com.example.comicbookrental.data.repositories.wishlist.WishlistRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WishlistViewModel : ViewModel() {

    private val repository: WishlistRepository = WishlistRepositoryImpl()

    private val _items = MutableStateFlow<List<Comic>>(emptyList())
    val items = _items.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _items.value = repository.getAll()
    }

    /** Toggles [comic] in the wishlist and returns the new favorite state (true = now favorited). */
    fun toggle(comic: Comic): Boolean {
        val nowFavorite = repository.toggle(comic)
        refresh()
        return nowFavorite
    }

    fun isFavorite(comicId: Int): Boolean = _items.value.any { it.id == comicId }
}
