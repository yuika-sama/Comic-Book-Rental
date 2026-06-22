package com.example.comicbookrental.ui.screens.rentals

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.data.entities.RentalEntity
import com.example.comicbookrental.ui.components.RentalCard
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme

@Composable
fun RentalsScreen(
    viewModel: RentalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MyRentalsContent(
        rentals = uiState.rentalList,
        onInsertTestRental = viewModel::insertTestRental
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MyRentalsScreenPreview() {

    val mockRentals = listOf(

        RentalEntity(
            rentalId = 1,
            comicId = 101,
            userId = 1,
            rentalDate = System.currentTimeMillis(),
            dueDate = System.currentTimeMillis() + 6048000000000000,
            status = "ACTIVE"
        ),

        RentalEntity(
            rentalId = 2,
            comicId = 102,
            userId = 1,
            rentalDate = System.currentTimeMillis(),
            dueDate = System.currentTimeMillis() + 1209600000,
            status = "ACTIVE"
        ),

        RentalEntity(
            rentalId = 3,
            comicId = 103,
            userId = 2,
            rentalDate = System.currentTimeMillis(),
            dueDate = System.currentTimeMillis() - 86400000,
            status = "EXPIRED"
        )
    )

    MyRentalsContent(
//        rentals = mockRentals,
        onInsertTestRental = {},
        rentals = emptyList()
    )
}
