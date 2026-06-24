package com.example.comicbookrental.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.comicbookrental.ui.screens.search.SearchRoute as SearchScreenEntry
import com.example.comicbookrental.ui.components.PanelRushTopBar
import com.example.comicbookrental.ui.components.PanelRushBottomBar
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.HankenGrotesk
import com.example.comicbookrental.ui.theme.Anton

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
                    onCheckoutClick = {
                        // TODO: Checkout logic
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