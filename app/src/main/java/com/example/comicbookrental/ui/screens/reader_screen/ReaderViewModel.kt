package com.example.comicbookrental.ui.screens.reader_screen

import androidx.lifecycle.ViewModel
import com.example.comicbookrental.data.mock.MockReaderData
import com.example.comicbookrental.domain.repository.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReaderViewModel : ViewModel() {

    private val rentalRepository: RentalRepository = RentalRepositoryImpl()

    private val _uiState = MutableStateFlow(ReaderUiState())
    val uiState = _uiState.asStateFlow()

    fun loadReader(rentalId: Int) {
        val rental = rentalRepository.getRentalById(rentalId)

        _uiState.value = _uiState.value.copy(
            rental = rental,
            pages = rental?.let {
                MockReaderData.getPagesForComic(it.comicId)
            }.orEmpty(),
            isLoading = false
        )
    }

    fun changeReadingMode(mode: ReadingMode) {
        _uiState.value = _uiState.value.copy(
            readingMode = mode
        )
    }
}