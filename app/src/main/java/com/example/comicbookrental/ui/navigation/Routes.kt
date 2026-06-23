package com.example.comicbookrental.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object AuthGraph
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object ForgetPassword
@Serializable data class InputOtp(val email: String)
@Serializable data class ChangePassword(val email: String)
@Serializable object Profile

@Serializable object CatalogGraph
@Serializable object HomeRoute
@Serializable data class ComicDetailRoute(val comicId: String)

@Serializable object RentalGraph
@Serializable object MyRentalsRoute
@Serializable data class ReaderRoute(val comicId: String)

// Common: Bottom bar
@Serializable object MainTabsStructure