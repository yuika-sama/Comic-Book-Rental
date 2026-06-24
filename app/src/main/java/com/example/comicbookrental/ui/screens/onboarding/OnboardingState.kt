package com.example.comicbookrental.ui.screens.onboarding

data class OnboardingState(
    val currentPage: Int = 0,
    val selectedGenres: Set<String> = emptySet()
)
