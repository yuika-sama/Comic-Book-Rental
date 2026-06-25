package com.example.comicbookrental.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import com.example.comicbookrental.ui.screens.admin.AdminScaffold
import com.example.comicbookrental.ui.screens.admin.dashboard.AdminDashboardScreen
import com.example.comicbookrental.ui.screens.admin.manage_comics.ManageComicsScreen
import com.example.comicbookrental.ui.screens.admin.manage_users.ManageUsersScreen
import com.example.comicbookrental.ui.screens.admin.profile.AdminProfileScreen
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.comicbookrental.data.repositories.checkout.CheckoutRepositoryImpl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.comicbookrental.ui.screens.home.HomeRoute as HomeScreenEntry
import com.example.comicbookrental.ui.screens.detail.ComicDetailRoute as ComicDetailScreenEntry
import com.example.comicbookrental.ui.screens.wishlist.WishlistRoute as WishlistScreenEntry

import com.example.comicbookrental.ui.screens.login.LoginScreen
import com.example.comicbookrental.ui.screens.register.RegisterScreen
import com.example.comicbookrental.ui.screens.forgot_password.ForgotPasswordScreen
import com.example.comicbookrental.ui.screens.notifications.NotificationScreen
import com.example.comicbookrental.ui.screens.rentals.MyRentalsScreen
import com.example.comicbookrental.ui.screens.verify_otp.VerifyOtpScreen
import com.example.comicbookrental.ui.screens.reset_password.ResetPasswordScreen
import com.example.comicbookrental.ui.screens.settings.SettingsScreen

fun NavGraphBuilder.authGraph(navController: NavHostController)
{
    navigation<AuthGraph>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginScreen(
                onRegisterClick = { navController.navigate(RegisterRoute) },
                onForgotPasswordClick = { navController.navigate(ForgetPassword) },
                onLoginSuccess = { isAdmin ->
                    if (isAdmin) {
                        navController.navigate(AdminGraph) {
                            popUpTo(AuthGraph) { inclusive = true }
                        }
                    } else {
                        navController.navigate(CatalogGraph) {
                            popUpTo(AuthGraph) { inclusive = true }
                        }
                    }
                },
                onNavigateToVerify = { email ->
                    navController.navigate(
                        VerifyOtp(
                            email = email,
                            isFromLogin = true
                        )
                    )
                }
            )
        }
        composable<RegisterRoute> {
            RegisterScreen(
                onLoginClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(LoginRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                }
            )
        }
        composable<ForgetPassword> {
            ForgotPasswordScreen(
                onBackToLoginClick = { navController.popBackStack() },
                onSendOtpClick = { email ->
                    navController.navigate(
                        VerifyOtp(
                            email = email,
                            isFromLogin = false
                        )
                    )
                }
            )
        }
        composable<VerifyOtp> { backStackEntry ->
            val route = backStackEntry.toRoute<VerifyOtp>()
            VerifyOtpScreen(
                email = route.email,
                onVerifySuccess = { isAdmin ->
                    if (route.isFromLogin)
                    {
                        navController.navigate(if (isAdmin) AdminGraph else CatalogGraph) {
                            popUpTo(AuthGraph) {
                                inclusive = true
                            }
                        }
                    }
                    else
                    {
                        navController.navigate(ChangePassword(route.email))
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<ChangePassword> { backStackEntry ->
            val route = backStackEntry.toRoute<ChangePassword>()
            ResetPasswordScreen(
                email = route.email,
                onPasswordResetSuccess = {
                    navController.navigate(LoginRoute) {
                        popUpTo(AuthGraph) { inclusive = true }
                    }
                },
                onCancelClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo(AuthGraph) { inclusive = true }
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.catalogGraph(
    navController: NavHostController
)
{
    navigation<CatalogGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreenEntry(
                onComicClick = { comicId ->
                    navController.navigate(ComicDetailRoute(comicId.toString()))
                }
            )
        }
        composable<ComicDetailRoute> {
            val checkoutRepository = remember { CheckoutRepositoryImpl() }
            ComicDetailScreenEntry(
                onBack = { navController.popBackStack() },
                onComicClick = { comicId ->
                    navController.navigate(ComicDetailRoute(comicId))
                },
                onCartClick = { navController.navigate(CartRoute) },
                onRentNow = { item ->
                    checkoutRepository.prepareDirectCheckout(item)
                    navController.navigate(CheckoutRoute)
                },
            )
        }

    }
}

fun NavGraphBuilder.rentalGraph(
    navController: NavHostController
)
{
    composable<MyRentalsRoute> {
        MyRentalsScreen(
            onNavigateToReader = { comicId ->
                navController.navigate(ReaderRoute(comicId.toString()))
            }
        )
    }

    composable<ReaderRoute> { backstackEntry ->
        val route = backstackEntry.toRoute<ReaderRoute>()
        com.example.comicbookrental.ui.screens.reader_screen.ReaderScreen(
            rentalId = route.comicId.toIntOrNull() ?: 0,
            onBackClick = { navController.popBackStack() },
            onExtendRentalClick = { rental ->
                // TODO: Extend rental logic
            }
        )
    }
}

fun NavGraphBuilder.profileExtensionsGraph(
    navController: NavHostController
)
{
    composable<WishlistRoute> {
        WishlistScreenEntry(
            onBack = { navController.popBackStack() },
            onComicClick = { comicId -> navController.navigate(ComicDetailRoute(comicId)) },
            onExplore = { navController.navigate(HomeRoute) },
            onCartClick = { navController.navigate(CartRoute) }
        )
    }

    composable<PaymentMethodsRoute> {
        // TODO: Payment Methods UI - Saved cards, Momo, Paypal (Section 8.1)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Linked Payment Methods Screen")
        }
    }

    composable<NotificationsRoute> {
        NotificationScreen()
    }

    composable<SettingsRoute> {
        SettingsScreen()
    }
}

fun NavGraphBuilder.adminGraph(
    navController: NavHostController,
    onLogout: () -> Unit
)
{
    navigation<AdminGraph>(startDestination = AdminDashboardRoute) {
        composable<AdminDashboardRoute> {
            AdminScaffold(navController = navController) { innerPadding ->
                AdminDashboardScreen(modifier = Modifier.padding(innerPadding))
            }
        }

        composable<AdminManageUsersRoute> {
            AdminScaffold(navController = navController) { innerPadding ->
                ManageUsersScreen(modifier = Modifier.padding(innerPadding))
            }
        }

        composable<AdminManageComicsRoute> {
            AdminScaffold(navController = navController) { innerPadding ->
                ManageComicsScreen(modifier = Modifier.padding(innerPadding))
            }
        }

        composable<AdminProfileRoute> {
            AdminScaffold(navController = navController) { innerPadding ->
                AdminProfileScreen(
                    onLogout = onLogout,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}