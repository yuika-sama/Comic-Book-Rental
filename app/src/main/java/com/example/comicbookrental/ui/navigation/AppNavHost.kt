package com.example.comicbookrental.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.comicbookrental.ui.components.commonComponents.PanelRushBottomBar
import com.example.comicbookrental.ui.components.commonComponents.PanelRushTopBar
import com.example.comicbookrental.ui.screens.cart.CartScreen
import com.example.comicbookrental.ui.screens.profile.ProfileScreen
import com.example.comicbookrental.ui.screens.profile_detail.ProfileDetailScreen
import com.example.comicbookrental.utils.StoreManager

@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context  = LocalContext.current
    val storeManager = remember { StoreManager(context) }
    val startGraph = if (storeManager.isLoggedIn()) CatalogGraph else AuthGraph

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
        topBar = {
            // TODO: showTopBar based on current route
            if (showBottomBar){
                Box(
                    modifier = Modifier.statusBarsPadding()
                ){
                    PanelRushTopBar(
                        onMenuClick = {
                            val currentRoute = currentDestination?.route
                            if (currentRoute != HomeRoute.toString()){
                                navController.navigate(HomeRoute)
                            } else {
                                Log.d(
                                    "Topbar Icon Click: ",
                                    "Current is home route"
                                )
                            }
                        },
                        onNotificationsClick = {
                            // TODO: Navigate to Notification Screen
                        },
                        onNavigateToCartClick = {
                            navController.navigate(CartRoute)
                        }
                    )
                }
            }
        },
        bottomBar = {
            if(showBottomBar){
                Box(
                    modifier = Modifier.navigationBarsPadding()
                ){
                    PanelRushBottomBar(
                        tabs=tabs,
                        currentDestination = currentDestination,
                        onTabClick = {tab ->
                            navController.navigate(tab.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startGraph,
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