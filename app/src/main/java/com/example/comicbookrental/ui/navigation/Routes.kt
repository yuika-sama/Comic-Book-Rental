package com.example.comicbookrental.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object AuthGraph
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object ForgetPassword
@Serializable data class VerifyOtp(val email: String)
@Serializable data class ChangePassword(val email: String)

@Serializable object ProfileRoute
@Serializable object ProfileDetailRoute

@Serializable object CartRoute

@Serializable object SearchRoute

@Serializable object CatalogGraph
@Serializable object HomeRoute
@Serializable data class ComicDetailRoute(val comicId: String)

@Serializable object RentalGraph
@Serializable object MyRentalsRoute
@Serializable data class ReaderRoute(val comicId: String)

// Onboarding & Guidance (Section 2.3)
@Serializable object OnboardingRoute

// Payment Success & Receipts (Section 4.3)
@Serializable data class PaymentSuccessRoute(val orderId: String, val price: Double, val comicTitle: String)

// Profile Settings & Extensions (Section 8.1 & 9)
@Serializable object WishlistRoute
@Serializable object PaymentMethodsRoute
@Serializable object NotificationsRoute

// Admin & Dashboard (Section 8.2)
@Serializable object AdminGraph
@Serializable object AdminDashboardRoute
@Serializable object AdminManageUsersRoute
@Serializable object AdminManageComicsRoute

// Common: Bottom bar
@Serializable object MainTabsStructure
