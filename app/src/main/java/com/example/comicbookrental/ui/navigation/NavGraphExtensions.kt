package com.example.comicbookrental.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.comicbookrental.ui.screens.home.HomeRoute as HomeScreenEntry
import com.example.comicbookrental.ui.screens.detail.ComicDetailRoute as ComicDetailScreenEntry

import com.example.comicbookrental.ui.screens.login.LoginScreen
import com.example.comicbookrental.ui.screens.register.RegisterScreen
import com.example.comicbookrental.ui.screens.forgot_password.ForgotPasswordScreen
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
                onSendOtpClick = { email -> navController.navigate(InputOtp(email)) }
            )
        }
        composable<InputOtp> { backStackEntry ->
            val route = backStackEntry.toRoute<InputOtp>()
            VerifyOtpScreen(
                email = route.email,
                onVerifySuccess = { navController.navigate(ChangePassword(route.email)) },
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

fun NavGraphBuilder.catalogGraph(navController: NavHostController){
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

fun NavGraphBuilder.rentalGraph(navController: NavHostController){
    // TODO: Navigation between rental graph
    composable<MyRentalsRoute> {

    }

    composable<ReaderRoute> {

    }
}