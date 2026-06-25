package com.example.comicbookrental.ui.components.checkoutComponents


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens

@Composable
 fun CheckoutEmptyContent(
    onBackClick: () -> Unit
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
                text = "NOTHING TO CHECKOUT",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Anton
                )
            )

            Text(
                text = "Select a comic or add comics to your cart first.",
                style = MaterialTheme.typography.bodyMedium
            )

            ComicButton(
                text = "Back",
                onClick = onBackClick,
                variant = ComicButtonVariant.Secondary
            )
        }
    }
}