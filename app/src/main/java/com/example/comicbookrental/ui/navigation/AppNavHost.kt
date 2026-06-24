package com.example.comicbookrental.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.comicbookrental.ui.screens.cart.CartScreen
import com.example.comicbookrental.ui.screens.profile.ProfileScreen
import com.example.comicbookrental.ui.screens.profile_detail.ProfileDetailScreen

@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val tabs = listOf(
        NavigationTab("Home", HomeRoute, Icons.Default.Home),
        NavigationTab("Search", SearchRoute, Icons.Default.Search),
        NavigationTab("Cart", CartRoute, Icons.Default.ShoppingCart),
        NavigationTab("Profile", ProfileRoute, Icons.Default.Person),
    )

    val showBottomBar = currentDestination?.hierarchy?.any() {dest ->
        dest.hasRoute<HomeRoute>() ||
        dest.hasRoute<MyRentalsRoute>() ||
        dest.hasRoute<CartRoute>() ||
        dest.hasRoute<ProfileRoute>() ||
        dest.hasRoute<SearchRoute>()
    } == true

    Scaffold(
        bottomBar = {
            if(showBottomBar){
                NavigationBar{
                    tabs.forEach { tab ->
                        val isSelected = currentDestination?.hierarchy?.any{
                            it.hasRoute(tab.route::class)
                        } == true

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(tab.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label
                                )
                            }
                        )
                    }
                }
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AuthGraph,
            modifier = Modifier.padding(innerPadding)
        ){
            authGraph(navController)
            catalogGraph(navController)
            rentalGraph(navController)

            composable<CartRoute> {
                CartScreen(
                    onCheckoutClick = {
                        // TODO: Checkout logic
                    }
                )
            }

            composable<ProfileRoute>{
                ProfileScreen(
                    onLogOut = {
                        navController.navigate(AuthGraph) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onProfileDetailClick = {
                        navController.navigate(ProfileDetailRoute)
                    },
                    onCartClick = {
                        navController.navigate(CartRoute)
                    },
                    onWishlistClick = {
                        // Wishlist navigation placeholder
                    },
                    onHistoryClick = {
                        navController.navigate(MyRentalsRoute)
                    }
                )
            }

            composable<ProfileDetailRoute> {
                ProfileDetailScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}