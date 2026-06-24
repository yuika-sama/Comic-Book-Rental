package com.example.comicbookrental.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.comicbookrental.data.repositories.checkout.CheckoutRepositoryImpl
import com.example.comicbookrental.ui.screens.cart.CartScreen
import com.example.comicbookrental.ui.screens.checkout.CheckoutScreen
import com.example.comicbookrental.ui.screens.profile.ProfileScreen
import com.example.comicbookrental.ui.screens.profile_detail.ProfileDetailScreen
import com.example.comicbookrental.ui.screens.search.SearchRoute as SearchScreenEntry
import com.example.comicbookrental.ui.components.commonComponents.PanelRushTopBar
import com.example.comicbookrental.ui.components.commonComponents.PanelRushBottomBar

@Composable
fun AppNavHost(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val checkoutRepository = remember { CheckoutRepositoryImpl() }

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
            if (showBottomBar) {
                PanelRushTopBar(
                    onMenuClick = { /* Handle menu click */ },
                    onNotificationsClick = {
                        navController.navigate(NotificationsRoute)
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                PanelRushBottomBar(
                    tabs = tabs,
                    currentDestination = currentDestination,
                    onTabClick = { tab ->
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
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
                    onCheckoutClick = { cartItems ->
                        checkoutRepository.prepareCartCheckout(cartItems)
                        navController.navigate(CheckoutRoute)
                    }
                )
            }

            composable<CheckoutRoute> {
                CheckoutScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onViewRentalsClick = {
                        navController.navigate(MyRentalsRoute) {
                            popUpTo(CheckoutRoute) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<SearchRoute> {
                SearchScreenEntry(
                    onComicClick = { comicId ->
                        navController.navigate(ComicDetailRoute(comicId.toString()))
                    },
                    onMenuClick = {
                        // Menu click placeholder
                    },
                    onNotificationsClick = {
                        navController.navigate(NotificationsRoute)
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
                        navController.navigate(WishlistRoute)
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

            // Profile extensions and admin graphs placeholders (Section 8.1 & 8.2)
            profileExtensionsGraph(navController)
            adminGraph(navController)
        }

    }
}
