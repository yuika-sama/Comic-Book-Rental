package com.example.comicbookrental.ui.screens.admin.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.adminComponents.AdminErrorState
import com.example.comicbookrental.ui.components.adminComponents.AdminLoadingState
import com.example.comicbookrental.ui.components.adminComponents.DashboardStatCard
import com.example.comicbookrental.ui.components.adminComponents.HotComicRow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.utils.toPriceLabel

@Composable
fun AdminDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: AdminDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is AdminDashboardUiState.Loading -> AdminLoadingState()

            is AdminDashboardUiState.Error -> AdminErrorState(
                message = (uiState as AdminDashboardUiState.Error).message
            )

            is AdminDashboardUiState.Content -> DashboardContent(
                stats = (uiState as AdminDashboardUiState.Content).stats
            )
        }
    }
}

@Composable
private fun DashboardContent(stats: DashboardStats) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.Spacing.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter)
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter)) {
                DashboardStatCard(
                    label = "Revenue",
                    value = stats.totalRevenue.toPriceLabel(),
                    icon = Icons.Default.Payments,
                    accent = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                DashboardStatCard(
                    label = "Total rentals",
                    value = stats.totalRentals.toString(),
                    icon = Icons.Default.MenuBook,
                    accent = MaterialTheme.extendedColors.info,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter)) {
                DashboardStatCard(
                    label = "Active rentals",
                    value = stats.activeRentals.toString(),
                    icon = Icons.Default.Bolt,
                    accent = MaterialTheme.extendedColors.success,
                    modifier = Modifier.weight(1f)
                )
                DashboardStatCard(
                    label = "Total users",
                    value = stats.totalUsers.toString(),
                    icon = Icons.Default.Group,
                    accent = MaterialTheme.extendedColors.warning,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Text(
                text = "HOT COMICS",
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = Anton),
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (stats.hotComics.isEmpty()) {
            item {
                Text(
                    text = "No comics to rank yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            itemsIndexed(stats.hotComics, key = { _, comic -> comic.id }) { index, comic ->
                HotComicRow(rank = index + 1, comic = comic)
            }
        }
    }
}
