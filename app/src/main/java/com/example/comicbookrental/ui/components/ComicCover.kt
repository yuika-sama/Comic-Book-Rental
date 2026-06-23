package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

/**
 * Ảnh bìa truyện. Tải [url] qua Coil và cắt cho lấp đầy khung; nếu URL rỗng (preview / data
 * thiếu ảnh) thì hiển thị ô màu placeholder thay vì để trống.
 *
 * Dùng để truyền vào slot `cover` của các card (FeaturedComicCard, NewReleaseCard,
 * RankedEpicCard), nên mặc định lấp đầy Box cha bằng [Modifier.fillMaxSize].
 */
@Composable
fun CartComicCover(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    if (url.isBlank()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
        )
    } else {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize(),
        )
    }
}
