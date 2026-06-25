package com.example.comicbookrental.ui.screens.admin.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.entities.AdminUser
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.data.entities.RentalStatus
import com.example.comicbookrental.domain.repository.AdminUserRepository
import com.example.comicbookrental.domain.repository.ComicRepository
import com.example.comicbookrental.domain.repository.RentalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
    private val userRepository: AdminUserRepository,
    private val rentalRepository: RentalRepository,
) : ViewModel()
{
    val uiState: StateFlow<AdminDashboardUiState> =
        combine(
            comicRepository.getAllComics(),
            userRepository.getUsers(),
        ) { comics, users ->
            AdminDashboardUiState.Content(buildStats(comics, users)) as AdminDashboardUiState
        }
            .catch { e -> emit(AdminDashboardUiState.Error(e.message ?: "Couldn't load dashboard")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AdminDashboardUiState.Loading,
            )

    init {
        viewModelScope.launch { comicRepository.seedIfEmpty() }
        viewModelScope.launch { userRepository.refresh() }
    }

    private fun buildStats(comics: List<Comic>, users: List<AdminUser>): DashboardStats {
        val rentals = rentalRepository.getAllRentals()

        val revenue = rentals.sumOf { rental ->
            val pricePerDay = comics.find { it.id == rental.comicId }?.rentalPrice ?: FALLBACK_PRICE
            val days = ((rental.dueDate - rental.rentalDate) / DAY_MS).coerceAtLeast(1)
            pricePerDay * days
        }

        return DashboardStats(
            totalRevenue = revenue,
            totalRentals = rentals.size,
            activeRentals = rentals.count { it.status == RentalStatus.ACTIVE },
            totalUsers = users.size,
            hotComics = comics.sortedByDescending { it.viewCount }.take(HOT_LIMIT),
        )
    }

    private companion object {
        const val DAY_MS = 24 * 60 * 60 * 1000L
        const val FALLBACK_PRICE = 2.0
        const val HOT_LIMIT = 5
    }
}
