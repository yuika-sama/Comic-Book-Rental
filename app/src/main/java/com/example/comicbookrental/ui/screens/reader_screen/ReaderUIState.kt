package com.example.comicbookrental.ui.screens.reader_screen

import com.example.comicbookrental.data.entities.ReaderPageEntity
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.data.entities.RentalStatus


enum class ReadingMode {
    HORIZONTAL,
    VERTICAL
}

data class ReaderUiState(
    val rental: Rental? = null,
    val pages: List<ReaderPageEntity> = emptyList(),
    val readingMode: ReadingMode = ReadingMode.HORIZONTAL,
    val isLoading: Boolean = true
)

fun Rental.isExpired(): Boolean {
    return status == RentalStatus.EXPIRED ||
            dueDate <= System.currentTimeMillis()
}