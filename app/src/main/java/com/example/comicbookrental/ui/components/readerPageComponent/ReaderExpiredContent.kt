package com.example.comicbookrental.ui.components.readerPageComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens


@Composable
fun ReaderExpiredContent(
    rental: Rental,
    onBackClick: () -> Unit,
    onExtendRentalClick: (Rental) -> Unit
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
                text = "ACCESS EXPIRED",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = Anton
                ),
                color = MaterialTheme.colorScheme.error
            )

            Text(
                text = rental.comicTitle,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "This rental period has ended. Extend it to continue reading.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            ComicButton(
                text = "Extend Rental",
                onClick = {
                    onExtendRentalClick(rental)
                },
                modifier = Modifier.fillMaxWidth(),
                variant = ComicButtonVariant.Primary
            )

            ComicButton(
                text = "Back To My Rentals",
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth(),
                variant = ComicButtonVariant.Secondary
            )
        }
    }
}