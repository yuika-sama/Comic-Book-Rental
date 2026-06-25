package com.example.comicbookrental.ui.screens.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.PaymentMethod
import com.example.comicbookrental.data.entities.rentalDays
import com.example.comicbookrental.data.entities.totalPrice

import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.cartComponents.CartComicCover
import com.example.comicbookrental.ui.components.checkoutComponents.CheckoutEmptyContent
import com.example.comicbookrental.ui.components.checkoutComponents.CheckoutSuccessContent

import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.utils.toDisplayDate
import com.example.comicbookrental.ui.utils.toVnd

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = viewModel(),
    onBackClick: () -> Unit,
    onViewRentalsClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isCompleted -> {
            CheckoutSuccessContent(
                receiptId = uiState.receiptId,
                items = uiState.completedItems,
                totalPrice = uiState.totalPrice,
                onViewRentalsClick = onViewRentalsClick
            )
        }

        uiState.checkoutItems.isEmpty() -> {
            CheckoutEmptyContent(
                onBackClick = onBackClick
            )
        }

        else -> {
            CheckoutContent(
                items = uiState.checkoutItems,
                totalPrice = uiState.totalPrice,
                selectedPaymentMethod = uiState.paymentMethod,
                onPaymentMethodClick = viewModel::selectPaymentMethod,
                onBackClick = onBackClick,
                onConfirmClick = viewModel::confirmCheckout
            )
        }
    }
}

@Composable
private fun CheckoutContent(
    items: List<CartItem>,
    totalPrice: Double,
    selectedPaymentMethod: PaymentMethod,
    onPaymentMethodClick: (PaymentMethod) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.Spacing.ScreenPadding)
    ) {
        Text(
            text = "CHECKOUT",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = Anton
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.22f)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        Text(
            text = "RENTAL SUMMARY",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = Anton
            )
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.ContentSpacing))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                Dimens.Spacing.ContentSpacing
            )
        ) {
            items(
                items = items,
                key = { it.comicId }
            ) { item ->
                CheckoutItemSummary(item = item)
            }
        }

        Spacer(modifier = Modifier.height(Dimens.Spacing.ContentSpacing))

        Text(
            text = "PAYMENT METHOD",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = Anton
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PaymentMethodButton(
                text = "CARD",
                selected = selectedPaymentMethod == PaymentMethod.CREDIT_CARD,
                onClick = {
                    onPaymentMethodClick(PaymentMethod.CREDIT_CARD)
                },
                modifier = Modifier.weight(1f)
            )

            PaymentMethodButton(
                text = "PAYPAL",
                selected = selectedPaymentMethod == PaymentMethod.PAYPAL,
                onClick = {
                    onPaymentMethodClick(PaymentMethod.PAYPAL)
                },
                modifier = Modifier.weight(1f)
            )

            PaymentMethodButton(
                text = "BANKING",
                selected = selectedPaymentMethod == PaymentMethod.SAVED_CARD,
                onClick = {
                    onPaymentMethodClick(PaymentMethod.SAVED_CARD)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        Text(
            text = "TOTAL: $ $totalPrice",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = Anton
            )
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.ContentSpacing))

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                Dimens.Spacing.ContentSpacing
            )
        ) {
            ComicButton(
                text = "Back",
                onClick = onBackClick,
                modifier = Modifier.weight(1f),
                variant = ComicButtonVariant.Secondary
            )

            ComicButton(
                text = "Pay Now",
                onClick = onConfirmClick,
                modifier = Modifier.weight(1f),
                variant = ComicButtonVariant.Primary
            )
        }
    }
}
@Composable
private fun CheckoutItemSummary(
    item: CartItem
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = Dimens.Border.Standard,
                color = ink,
                shape = shape
            )
            .padding(Dimens.Spacing.ContentSpacing),
        horizontalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ContentSpacing
        )
    ) {
        CartComicCover(
            imageUrl = item.comicCoverUrl,
            title = item.comicTitle,
            modifier = Modifier
                .width(90.dp)
                .height(135.dp)
                .clip(RoundedCornerShape(Dimens.Radius.Button))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(Dimens.Radius.Button)
                )
        )

        Column(
            modifier = Modifier.weight(0.72f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.comicTitle.uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "#Issue - ${item.comicId}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "${item.startDate.toDisplayDate()} → ${item.endDate.toDisplayDate()}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "${item.rentalDays()} DAYS",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = Anton
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "$ ${item.totalPrice()}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                )
            )
        }
    }
}
@Composable
private fun PaymentMethodButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Button)

    Box(
        modifier = modifier
            .height(42.dp)
            .clip(shape)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = Dimens.Border.Standard,
                color = ink,
                shape = shape
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