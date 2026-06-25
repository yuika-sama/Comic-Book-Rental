package com.example.comicbookrental.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comicbookrental.ui.components.commonComponents.halftoneBackground
import com.example.comicbookrental.ui.components.onboardingComponents.ChooseSectorPage
import com.example.comicbookrental.ui.components.onboardingComponents.DiscoverSagaPage
import com.example.comicbookrental.ui.components.onboardingComponents.ImmersiveReadingPage
import com.example.comicbookrental.ui.components.onboardingComponents.ManageLegacyPage

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
                onNext = { viewModel.nextPage() },
                onSkip = { viewModel.skip() }
            )

            3 -> ChooseSectorPage(
                selectedGenres = uiState.selectedGenres,
                onGenreClick = { viewModel.toggleGenre(it) },
                onComplete = onComplete
            )
        }
    }
}

