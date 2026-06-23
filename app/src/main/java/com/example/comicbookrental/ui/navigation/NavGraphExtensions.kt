package com.example.comicbookrental.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
// Aliased: route object `HomeRoute` (this package) vs the screen composable of the same name.
import com.example.comicbookrental.ui.screens.home.HomeRoute as HomeScreenEntry

import com.example.comicbookrental.ui.screens.auth.login.LoginScreen
import com.example.comicbookrental.ui.screens.auth.RegisterScreen
import com.example.comicbookrental.ui.screens.auth.ForgotPasswordScreen
import com.example.comicbookrental.ui.screens.auth.VerifyOtpScreen
import com.example.comicbookrental.ui.screens.auth.ResetPasswordScreen

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
                onSendOtpClick = { navController.navigate(InputOtp) }
            )
        }
        composable<InputOtp> {
            VerifyOtpScreen(
                onVerifySuccess = { navController.navigate(ChangePassword) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<ChangePassword> {
            ResetPasswordScreen(
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
            // TODO: Comic detail screen
            Text("Comic detail")
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