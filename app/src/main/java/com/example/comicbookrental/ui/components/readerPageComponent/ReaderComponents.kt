package com.example.comicbookrental.ui.components.readerPageComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.screens.reader_screen.ReadingMode
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
 fun ReaderLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
 fun ReaderNotFound(
    onBackClick: () -> Unit
) {
    ReaderMessage(
        title = "RENTAL NOT FOUND",
        message = "This rental may have been removed.",
        onBackClick = onBackClick
    )
}

@Composable
 fun ReaderNoPages(
    onBackClick: () -> Unit
) {
    ReaderMessage(
        title = "NO PAGES AVAILABLE",
        message = "There are no comic pages available yet.",
        onBackClick = onBackClick
    )
}

@Composable
 fun ReaderMessage(
    title: String,
    message: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Anton
                )
            )

            Text(
                text = message,
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