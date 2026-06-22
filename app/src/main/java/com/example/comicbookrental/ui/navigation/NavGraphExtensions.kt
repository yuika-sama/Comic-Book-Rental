package com.example.comicbookrental.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
// Aliased: route object `HomeRoute` (this package) vs the screen composable of the same name.
import com.example.comicbookrental.ui.screens.home.HomeRoute as HomeScreenEntry

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