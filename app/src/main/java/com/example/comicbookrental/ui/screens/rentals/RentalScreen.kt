package com.example.comicbookrental.ui.screens.rentals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comicbookrental.data.models.Rental
import com.example.comicbookrental.data.models.RentalStatus
import com.example.comicbookrental.ui.components.RentalCard
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

private enum class RentalTab {
    ACTIVE,
    HISTORY
}

@Composable
fun MyRentalsScreen(
    viewModel: RentalViewModel = viewModel(),
    onNavigateToReader: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    MyRentalsContent(
        rentals = uiState.rentalList,
        onReadClick = { rental ->
            onNavigateToReader(rental.rentalId)
        },
        onExtendClick = { rental ->
            viewModel.extendRental(rental.rentalId)
        }
    )
}

@Composable
private fun MyRentalsContent(
    rentals: List<Rental>,
    onReadClick: (Rental) -> Unit,
    onExtendClick: (Rental) -> Unit,
) {
    var selectedTab by remember {
        mutableStateOf(RentalTab.ACTIVE)
    }

    val now = System.currentTimeMillis()

    val activeRentals = rentals.filter {
        it.status == RentalStatus.ACTIVE && it.dueDate > now
    }

    val historyRentals = rentals.filter {
        it.status == RentalStatus.EXPIRED || it.dueDate <= now
    }

    val displayedRentals = when (selectedTab) {
        RentalTab.ACTIVE -> activeRentals
        RentalTab.HISTORY -> historyRentals
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.Spacing.ScreenPadding)
    ) {
        Text(
            text = "MY RENTALS",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = Anton
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.18f)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        Row(modifier = Modifier.fillMaxWidth()) {
            RentalTabButton(
                text = "ACTIVE",
                selected = selectedTab == RentalTab.ACTIVE,
                onClick = { selectedTab = RentalTab.ACTIVE },
                modifier = Modifier.weight(1f)
            )

            RentalTabButton(
                text = "HISTORY",
                selected = selectedTab == RentalTab.HISTORY,
                onClick = { selectedTab = RentalTab.HISTORY },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        if (displayedRentals.isEmpty()) {
            RentalEmptyState(
                isHistory = selectedTab == RentalTab.HISTORY,

                )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    Dimens.Spacing.SectionSpacing
                )
            ) {
                items(
                    items = displayedRentals,
                    key = { it.rentalId }
                ) { rental ->
                    RentalCard(
                        rental = rental,
                        onReadClick = { onReadClick(rental) },
                        onExtendClick = { onExtendClick(rental) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RentalTabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ink = MaterialTheme.extendedColors.ink

    Box(
        modifier = modifier
            .height(44.dp)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = Dimens.Border.Standard,
                color = ink
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = Anton
            ),
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
private fun RentalEmptyState(
    isHistory: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isHistory) "NO HISTORY" else "NO ACTIVE RENTALS",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Anton
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isHistory) {
                    "Your completed and expired rentals will appear here."
                } else {
                    "Rent a comic to start reading."
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Preview
@Composable
fun RentalPreview(){
    MyRentalsScreen()
}