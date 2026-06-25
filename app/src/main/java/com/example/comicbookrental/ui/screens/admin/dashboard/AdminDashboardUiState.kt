package com.example.comicbookrental.ui.screens.admin.dashboard

import com.example.comicbookrental.data.entities.Comic

data class DashboardStats(
    val totalRevenue: Double,
    val totalRentals: Int,
    val activeRentals: Int,
    val totalUsers: Int,
    val hotComics: List<Comic>,
)

sealed interface AdminDashboardUiState {
    data object Loading : AdminDashboardUiState

    data class Content(val stats: DashboardStats) : AdminDashboardUiState

    data class Error(val message: String) : AdminDashboardUiState
}
