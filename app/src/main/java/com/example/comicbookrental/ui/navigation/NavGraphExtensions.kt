package com.example.comicbookrental.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.comicbookrental.ui.screens.rentals.MyRentalsScreen
import com.example.comicbookrental.ui.screens.verify_otp.VerifyOtpScreen
import com.example.comicbookrental.ui.screens.reset_password.ResetPasswordScreen

fun NavGraphBuilder.authGraph(navController: NavHostController){
    navigation<AuthGraph>(startDestination = LoginRoute){
        composable <LoginRoute>{
            LoginScreen(
                onRegisterClick = { navController.navigate(RegisterRoute) },
                onForgotPasswordClick = { navController.navigate(ForgetPassword) },
                onLoginSuccess = {
                    navController.navigate(CatalogGraph) {
                        popUpTo(AuthGraph) { inclusive = true }
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
                onVerifySuccess = {
                    if (route.isFromLogin){
                        navController.navigate(CatalogGraph){
                            popUpTo(AuthGraph){
                                inclusive = true
                            }
                        }
                    } else {
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
){
    navigation<CatalogGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreenEntry(
                onComicClick = { comicId ->
                    navController.navigate(ComicDetailRoute(comicId.toString()))
                }
            )
        }
        composable<ComicDetailRoute> {
            ComicDetailScreenEntry(
                onBack = { navController.popBackStack() },
                onComicClick = { comicId ->
                    navController.navigate(ComicDetailRoute(comicId))
                },
            )
        }
    }
}

fun NavGraphBuilder.rentalGraph(
    navController: NavHostController
){
    // TODO: Navigation between rental graph
    composable<MyRentalsRoute> {
        MyRentalsScreen(
            onNavigateToReader = { comicId ->
                navController.navigate(ReaderRoute(comicId.toString()))
            }
        )
    }

    composable<ReaderRoute> { backstackEntry ->
        val route = backstackEntry.toRoute<ReaderRoute>()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Reading comic ${route.comicId}"
            )
        }
    }
}

fun NavGraphBuilder.profileExtensionsGraph(
    navController: NavHostController
) {
    composable<WishlistRoute> {
        WishlistScreenEntry(
            onBack = { navController.popBackStack() },
            onComicClick = { comicId -> navController.navigate(ComicDetailRoute(comicId)) },
            onExplore = { navController.navigate(HomeRoute) },
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
        // TODO: Notification Settings / Center UI - Turn on/off Push or Email alerts (Section 8.1)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Notification Settings")
        }
    }
}

fun NavGraphBuilder.adminGraph(
    navController: NavHostController
) {
    navigation<AdminGraph>(startDestination = AdminManageComicsRoute) {
        composable<AdminDashboardRoute> {
            // TODO: Admin Dashboard UI - Dashboard summary & reports analytics (Section 8.2)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Admin Dashboard Overview")
            }
        }

        composable<AdminManageUsersRoute> {
            // TODO: Admin Manage Users UI - Ban/Unban, check records (Section 8.2)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Admin: Manage Users")
            }
        }

        composable<AdminManageComicsRoute> {
            // TODO: Admin Manage Comics UI - Add new chapters, catalog terms (Section 8.2)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Admin: Manage Comics Catalog")
            }
        }
    }
}