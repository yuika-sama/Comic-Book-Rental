package com.example.comicbookrental.ui.screens.reader_screen

import com.example.comicbookrental.ui.components.readerPageComponent.ReaderSettingsSheet
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
import com.example.comicbookrental.ui.components.commonComponents.PanelRushTopBarBack
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.roundToInt

@Composable
fun ReaderScreen(
    rentalId: Int,
    onBackClick: () -> Unit,
    onExtendRentalClick: (Rental) -> Unit,
    viewModel: ReaderViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val rental = uiState.rental

    LaunchedEffect(rentalId) {
        viewModel.loadReader(rentalId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PanelRushTopBarBack(
            onMenuClick = onBackClick,
            onNotificationsClick = {}
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                uiState.isLoading -> ReaderLoading()

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
                        onReadingModeChange = viewModel::changeReadingMode
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun ReaderContent(
    rental: Rental,
    pages: List<ReaderPageEntity>,
    readingMode: ReadingMode,
    onReadingModeChange: (ReadingMode) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { pages.size }
    )

    var zoom by remember {
        mutableFloatStateOf(1f)
    }

    var brightness by remember {
        mutableFloatStateOf(1f)
    }

    var showSettings by remember {
        mutableStateOf(false)
    }

// Overload mới: centroid, zoomChange, panChange, rotationChange
    val transformableState = rememberTransformableState {
            _,
            zoomChange,
            _,
            _ ->

        zoom = (zoom * zoomChange).coerceIn(1f, 3f)
    }

    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimens.Spacing.ContentSpacing
                ),
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(ink)
        ) {
            when (readingMode) {
                ReadingMode.HORIZONTAL -> {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageIndex ->
                        ZoomableReaderPage(
                            page = pages[pageIndex],
                            zoom = zoom,
                            brightness = brightness,
                            transformableState = transformableState
                        )
                    }
                }

                ReadingMode.VERTICAL -> {
                    VerticalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageIndex ->
                        ZoomableReaderPage(
                            page = pages[pageIndex],
                            zoom = zoom,
                            brightness = brightness,
                            transformableState = transformableState
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    showSettings = true
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.92f
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = ink,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Reader settings",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showSettings) {
        ModalBottomSheet(
            onDismissRequest = {
                showSettings = false
            }
        ) {
            ReaderSettingsSheet(
                readingMode = readingMode,
                zoom = zoom,
                brightness = brightness,
                onReadingModeChange = onReadingModeChange,
                onZoomOutClick = {
                    zoom = (zoom - 0.25f).coerceAtLeast(1f)
                },
                onZoomInClick = {
                    zoom = (zoom + 0.25f).coerceAtMost(3f)
                },
                onResetZoomClick = {
                    zoom = 1f
                },
                onBrightnessChange = {
                    brightness = it.coerceIn(0.25f, 1f)
                }
            )
        }
    }

}

@Composable
 fun ZoomableReaderPage(
    page: ReaderPageEntity,
    zoom: Float,
    brightness: Float,
    transformableState: TransformableState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clip(RoundedCornerShape(Dimens.Radius.Card))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .transformable(
                    state = transformableState,
                    canPan = { _ -> false }
                )
                .graphicsLayer {
                    scaleX = zoom
                    scaleY = zoom

                    // Nền phía sau là đen.
                    // Alpha thấp hơn => trang truyện tối hơn.
                    alpha = brightness
                }
        ) {
            ReaderPageItem(
                page = page
            )
        }
    }

}
@Composable
 fun ReaderZoomBrightnessControls(
    zoom: Float,
    brightness: Float,
    onZoomChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onResetZoom: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.Spacing.ScreenPadding,
                vertical = Dimens.Spacing.ContentSpacing
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ZOOM: ${(zoom * 100).roundToInt()}%",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = Anton
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onZoomChange(zoom - 0.25f)
                    },
                    enabled = zoom > 1f
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Zoom out"
                    )
                }

                IconButton(
                    onClick = {
                        onZoomChange(zoom + 0.25f)
                    },
                    enabled = zoom < 3f
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Zoom in"
                    )
                }

                TextButton(
                    onClick = onResetZoom
                ) {
                    Text("RESET")
                }
            }
        }

        Text(
            text = "PINCH WITH TWO FINGERS TO ZOOM",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.size(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "BRIGHTNESS",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = Anton
                )
            )

            Text(
                text = "${(brightness * 100).roundToInt()}%",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Slider(
            value = brightness,
            onValueChange = onBrightnessChange,
            valueRange = 0.25f..1f
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ReaderContentPreview() {
    ComicBookRentalTheme {
        ReaderScreen(
            rentalId = 3,
            onBackClick = {},
            onExtendRentalClick = {},
            )
    }
}
