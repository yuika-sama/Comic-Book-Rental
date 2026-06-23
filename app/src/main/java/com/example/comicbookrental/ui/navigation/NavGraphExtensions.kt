package com.example.comicbookrental.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
// Aliased: route objects (this package) vs the screen composables of the same name.
import com.example.comicbookrental.ui.screens.home.HomeRoute as HomeScreenEntry
import com.example.comicbookrental.ui.screens.detail.ComicDetailRoute as ComicDetailScreenEntry

fun NavGraphBuilder.authGraph(navController: NavHostController){
    navigation<AuthGraph>(startDestination = LoginRoute){
        composable <LoginRoute>{
            // TODO: Navigation between auth graph
            Text("Login")
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