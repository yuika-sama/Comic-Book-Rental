package com.example.comicbookrental.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.components.commonComponents.halftoneBackground
import com.example.comicbookrental.ui.components.onboardingComponents.ChooseSectorPage
import com.example.comicbookrental.ui.components.onboardingComponents.DiscoverSagaPage
import com.example.comicbookrental.ui.components.onboardingComponents.ImmersiveReadingPage
import com.example.comicbookrental.ui.components.onboardingComponents.ManageLegacyPage
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
)
{
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .halftoneBackground()
    ) {
        when (uiState.currentPage)
        {
            0 -> DiscoverSagaPage(
                onNext = { viewModel.nextPage() },
                onSkip = { viewModel.skip() }
            )

            1 -> ImmersiveReadingPage(
                onNext = { viewModel.nextPage() },
                onSkip = { viewModel.skip() }
            )

            2 -> ManageLegacyPage(
                onNext = { viewModel.nextPage() }
            )

            3 -> ChooseSectorPage(
                selectedGenres = uiState.selectedGenres,
                onGenreClick = { viewModel.toggleGenre(it) },
                onComplete = onComplete
            )
        }
    }
}

