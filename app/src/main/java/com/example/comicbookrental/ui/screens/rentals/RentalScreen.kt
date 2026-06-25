package com.example.comicbookrental.ui.screens.rentals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.data.entities.RentalStatus
import com.example.comicbookrental.ui.components.rentalComponents.ExtendRentalDialog
import com.example.comicbookrental.ui.components.rentalComponents.RentalCard
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
    onNavigateToReader: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    onExtensionCheckout: (CartItem) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()


    var extendingRental by remember {
        mutableStateOf<Rental?>(null)
    }

    MyRentalsContent(
        rentals = uiState.rentalList,
        onReadClick = { rental ->
            onNavigateToReader(rental.rentalId)
        },
        onExtendClick = { selectedRental ->
            extendingRental = selectedRental
        },
        onBackClick = onBackClick
    )

    extendingRental?.let { rental ->
        ExtendRentalDialog(
            rentalTitle = rental.comicTitle,
            onDismiss = {
                extendingRental = null
            },
            onConfirm = { extraDays ->
                val extensionItem = viewModel.createExtensionCheckoutItem(
                    rental = rental,
                    extraDays = extraDays
                )

                extendingRental = null
                onExtensionCheckout(extensionItem)
            }
        )
    }


}

@Composable
private fun MyRentalsContent(
    rentals: List<Rental>,
    onReadClick: (Rental) -> Unit,
    onExtendClick: (Rental) -> Unit,
    onBackClick: () -> Unit
) {
    var selectedTab by remember {
        mutableStateOf(RentalTab.ACTIVE)
    }


    val now = System.currentTimeMillis()

    val activeRentals = rentals.filter { rental ->
        rental.status == RentalStatus.ACTIVE &&
                rental.dueDate > now
    }

    val historyRentals = rentals.filter { rental ->
        rental.status == RentalStatus.EXPIRED ||
                rental.dueDate <= now
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MY RENTALS",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = Anton
                ),
                modifier = Modifier.weight(1f)
            )

            RentalTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }

        Spacer(
            modifier = Modifier.height(
                Dimens.Spacing.SectionSpacing
            )
        )

        if (displayedRentals.isEmpty()) {
            RentalEmptyState(
                isHistory = selectedTab == RentalTab.HISTORY
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
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
                        onReadClick = onReadClick,
                        onExtendClick = onExtendClick
                    )
                }
            }
        }
    }

}

@Composable
private fun RentalTabs(
    selectedTab: RentalTab,
    onTabSelected: (RentalTab) -> Unit
) {
    val ink = MaterialTheme.extendedColors.ink

    Row(
        modifier = Modifier
            .width(145.dp)
            .height(30.dp)
            .border(
                width = Dimens.Border.Standard,
                color = ink
            )
    ) {
        RentalTabButton(
            text = "ACTIVE",
            selected = selectedTab == RentalTab.ACTIVE,
            onClick = {
                onTabSelected(RentalTab.ACTIVE)
            },
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(Dimens.Border.Standard)
                .background(ink)
        )

        RentalTabButton(
            text = "HISTORY",
            selected = selectedTab == RentalTab.HISTORY,
            onClick = {
                onTabSelected(RentalTab.HISTORY)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RentalTabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
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
                text = if (isHistory) {
                    "NO HISTORY"
                } else {
                    "NO ACTIVE RENTALS"
                },
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Anton
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

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