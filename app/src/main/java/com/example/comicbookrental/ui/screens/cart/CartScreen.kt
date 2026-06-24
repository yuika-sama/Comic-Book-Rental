package com.example.comicbookrental.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.cartComponents.CartItemCard
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.utils.toVnd


@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(),
    onCheckoutClick: (List<CartItem>) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    var editingItem by remember {
        mutableStateOf<CartItem?>(null)
    }

    // Khi quay lại CartScreen, đọc lại mock cart mới nhất.
    LaunchedEffect(Unit) {
        viewModel.refreshCart()
    }

    CartContent(
        cartItems = uiState.cartItems,
        totalPrice = uiState.totalPrice,
        onRemoveItem = viewModel::removeFromCart,
        onEditDatesClick = { item ->
            editingItem = item
        },
        onCheckoutClick = onCheckoutClick
    )

    editingItem?.let { item ->
        ComicDateRangePickerDialog(
            comicTitle = item.comicTitle,
            initialStartDate = item.startDate,
            initialEndDate = item.endDate,
            onDismiss = {
                editingItem = null
            },
            onConfirm = { startDate, endDate ->
                viewModel.updateRentalDates(
                    comicId = item.comicId,
                    startDate = startDate,
                    endDate = endDate
                )

                editingItem = null
            }
        )
    }
}

@Composable
private fun CartContent(
    cartItems: List<CartItem>,
    totalPrice: Long,
    onRemoveItem: (Int) -> Unit,
    onEditDatesClick: (CartItem) -> Unit,
    onCheckoutClick: (List<CartItem>) -> Unit
) {
    if (cartItems.isEmpty()) {
        CartEmptyState()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.Spacing.ScreenPadding)
    ) {
        Text(
            text = "YOUR CART",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = Anton
            )

        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                ,
            verticalArrangement = Arrangement.spacedBy(
                Dimens.Spacing.SectionSpacing
            )
        ) {
            items(
                items = cartItems,
                key = { it.comicId }
            ) { item ->
                CartItemCard(
                    item = item,
                    onEditDatesClick = {
                        onEditDatesClick(item)
                    },
                    onRemoveClick = {
                        onRemoveItem(item.comicId)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

        Text(
            text = "TOTAL: ${totalPrice.toVnd()}",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = Anton
            )
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

        ComicButton(
            text = "Proceed To Checkout",
            onClick = {
                onCheckoutClick(cartItems)
            },
            modifier = Modifier.fillMaxWidth(),
            variant = ComicButtonVariant.Primary
        )
    }
}

@Composable
private fun CartEmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "YOUR CART IS EMPTY",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Anton
                )
            )

            Spacer(
                modifier = Modifier.height(
                    Dimens.Spacing.StackMd
                )
            )

            Text(
                text = "Add comics from the detail screen to rent them.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
