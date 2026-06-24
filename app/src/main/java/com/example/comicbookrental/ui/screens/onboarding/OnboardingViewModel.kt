package com.example.comicbookrental.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel()
{
    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun setPage(page: Int)
    {
        _uiState.update { it.copy(currentPage = page) }
    }

    fun skip()
    {
        _uiState.update { it.copy(currentPage = 3) }
    }

    fun nextPage()
    {
        _uiState.update {
            if (it.currentPage < 3) it.copy(currentPage = it.currentPage + 1) else it
        }
    }

    fun previousPage()
    {
        _uiState.update {
            if (it.currentPage > 0) it.copy(currentPage = it.currentPage - 1) else it
        }
    }

    fun toggleGenre(genre: String)
    {
        _uiState.update { state ->
            val newGenres = state.selectedGenres.toMutableSet()
            if (newGenres.contains(genre))
            {
                newGenres.remove(genre)
            }
            else
            {
                newGenres.add(genre)
            }
            state.copy(selectedGenres = newGenres)
        }
    }
}
