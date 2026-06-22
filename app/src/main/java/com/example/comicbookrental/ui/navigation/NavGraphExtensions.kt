package com.example.comicbookrental.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.authGraph(navController: NavHostController){
    navigation<AuthGraph>(startDestination = LoginRoute){
        composable <LoginRoute>{
            // TODO: Navigation between auth graph
            Text("Login")
        }
    }
}

fun NavGraphBuilder.catalogGraph(navController: NavHostController){
    // TODO: Navigation between catalog graph
    composable<HomeRoute> {

    }
    composable<ComicDetailRoute> {

    }
}

fun NavGraphBuilder.rentalGraph(navController: NavHostController){
    // TODO: Navigation between rental graph
    composable<MyRentalsRoute> {

    }

    composable<ReaderRoute> {

    }
}