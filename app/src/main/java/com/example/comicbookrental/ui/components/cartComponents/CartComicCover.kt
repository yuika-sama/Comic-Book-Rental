package com.example.comicbookrental.ui.components.cartComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun CartComicCover(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    if (imageUrl.isBlank()) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$title cover",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}