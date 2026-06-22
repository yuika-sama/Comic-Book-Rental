package com.example.comicbookrental.ui.screens.rentals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comicbookrental.data.models.Rental
import com.example.comicbookrental.ui.components.RentalCard

@Composable
fun RentalsScreen(
    viewModel: RentalViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RentalsContent(
        rentals = uiState.rentalList,
        onInsertTestRental = viewModel::insertTestRental,
        onDeleteRental = viewModel::deleteRental
    )
}


@Composable
fun RentalsContent(
    rentals: List<Rental>,
    onInsertTestRental: () -> Unit,
    onDeleteRental: (Int) -> Unit
) {
    if (rentals.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("📚", style =
                    MaterialTheme.typography.displayLarge)
                Spacer(Modifier.height(16.dp))
                Text("No Rentals Yet", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("Browse comics and rent your first title.")
                Spacer(Modifier.height(16.dp))

                Button(onClick = onInsertTestRental) {
                    Text("Insert Test Rental")
                }
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            rentals,
            key = { it.rentalId }
        ) { rental ->
            RentalCard(
                rental = rental,
                onDeleteClick = {
                    onDeleteRental(rental.rentalId)
                }
            )
        }
    }
}