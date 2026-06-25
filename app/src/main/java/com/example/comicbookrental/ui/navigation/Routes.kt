package com.example.comicbookrental.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object AuthGraph
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object ForgetPassword
@Serializable data class VerifyOtp(
    val email: String,
    val isFromLogin: Boolean = false
)
@Serializable data class ChangePassword(val email: String)

@Serializable object ProfileRoute
@Serializable object WishlistRoute
@Serializable object PaymentMethodsRoute
@Serializable object NotificationsRoute
@Serializable object SettingsRoute
@Serializable object ProfileDetailRoute

@Serializable object CartRoute
@Serializable object CheckoutRoute

@Serializable object SearchRoute

@Serializable object CatalogGraph
@Serializable object HomeRoute
@Serializable data class ComicDetailRoute(val comicId: String)

@Serializable object RentalGraph
@Serializable object MyRentalsRoute
@Serializable data class ReaderRoute(val comicId: String)

@Serializable object AdminGraph
@Serializable object AdminDashboardRoute
@Serializable object AdminManageUsersRoute
@Serializable object AdminManageComicsRoute
@Serializable object AdminProfileRoute

@Serializable data class PaymentSuccessRoute(val orderId: String, val price: Double, val comicTitle: String)
@Serializable object OnboardingRoute
