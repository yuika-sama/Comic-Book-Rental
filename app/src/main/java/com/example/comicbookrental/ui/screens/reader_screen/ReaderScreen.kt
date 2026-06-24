package com.example.comicbookrental.ui.screens.reader_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.data.entities.ReaderPageEntity
import com.example.comicbookrental.data.entities.RentalStatus
import com.example.comicbookrental.ui.components.readerPageComponent.ReaderExpiredContent
import com.example.comicbookrental.ui.components.readerPageComponent.ReaderLoading
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.components.readerPageComponent.ReaderModeSelector
import com.example.comicbookrental.ui.components.readerPageComponent.ReaderNoPages
import com.example.comicbookrental.ui.components.readerPageComponent.ReaderNotFound
import com.example.comicbookrental.ui.components.readerPageComponent.ReaderPageItem
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Primary

@Composable
fun ReaderScreen(
    rentalId: Int  ,
    onBackClick: () -> Unit,
    onExtendRentalClick: (Rental) -> Unit,
    viewModel: ReaderViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val rental = uiState.rental

    LaunchedEffect(rentalId) {
        viewModel.loadReader(rentalId)
    }

    when {
        uiState.isLoading -> {
            ReaderLoading()
        }

        rental == null -> {
            ReaderNotFound(
                onBackClick = onBackClick
            )
        }

        rental.isExpired() -> {
            ReaderExpiredContent(
                rental = rental,
                onBackClick = onBackClick,
                onExtendRentalClick = onExtendRentalClick
            )
        }

        uiState.pages.isEmpty() -> {
            ReaderNoPages(
                onBackClick = onBackClick
            )
        }

        else -> {
            ReaderContent(
                rental = rental,
                pages = uiState.pages,
                readingMode = uiState.readingMode,
                onReadingModeChange = viewModel::changeReadingMode,
            )
        }
    }
}

@Composable
private fun ReaderContent(
    rental: Rental,
    pages: List<ReaderPageEntity>,
    readingMode: ReadingMode,
    onReadingModeChange: (ReadingMode) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )

    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer( modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.Spacing.ContentSpacing
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = rental.comicTitle.uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = Anton
                    ),
                    color = Primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "PAGE ${pagerState.currentPage + 1} / ${pages.size}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }

        ReaderModeSelector(
            selectedMode = readingMode,
            onModeChange = onReadingModeChange
        )

        Spacer( modifier = Modifier.size(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ink)

        ) {
            when (readingMode) {
                ReadingMode.HORIZONTAL -> {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageIndex ->
                        ReaderPageItem(
                            page = pages[pageIndex]
                        )
                    }
                }

                ReadingMode.VERTICAL -> {
                    VerticalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageIndex ->
                        ReaderPageItem(
                            page = pages[pageIndex]
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReaderContentPreview() {
    ComicBookRentalTheme {
        ReaderContent(
            rental = Rental(
                rentalId = 1,
                comicId = 1,
                userId = 1,
                comicTitle = "Solo Leveling",
                comicCoverUrl = "",
                rentalDate = System.currentTimeMillis(),
                dueDate = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L,
                status = RentalStatus.ACTIVE
            ),
            pages = listOf(
                ReaderPageEntity(
                    pageNumber = 1,
                    imageUrl = ""
                ),
                ReaderPageEntity(
                    pageNumber = 2,
                    imageUrl = ""
                )
            ),
            readingMode = ReadingMode.HORIZONTAL,
            onReadingModeChange = {},
        )
    }
}
