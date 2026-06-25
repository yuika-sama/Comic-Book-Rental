package com.example.comicbookrental.ui.components.checkoutComponents


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.rentalDays
import com.example.comicbookrental.data.entities.totalPrice

import com.example.comicbookrental.ui.components.cartComponents.CartComicCover
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.utils.toDisplayDate
import com.example.comicbookrental.ui.utils.toVnd

@Composable
 fun CheckoutSuccessContent(
    receiptId: String,
    items: List<CartItem>,
    totalPrice: Double,
    onViewRentalsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.Spacing.ScreenPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                Dimens.Spacing.ContentSpacing
            )
        ) {
            Text(
                text = "PAYMENT COMPLETE!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = Anton
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Receipt: $receiptId",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "${items.size} COMIC(S) RENTED",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                )
            )

            Text(
                text = "TOTAL PAID: $ $totalPrice ",
                style = MaterialTheme.typography.titleMedium
            )

            ComicButton(
                text = "View My Rentals",
                onClick = onViewRentalsClick,
                modifier = Modifier.fillMaxWidth(),
                variant = ComicButtonVariant.Primary
            )
        }
    }
}