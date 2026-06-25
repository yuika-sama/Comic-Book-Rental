package com.example.comicbookrental.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.comicbookrental.data.repositories.checkout.CheckoutRepositoryImpl
import com.example.comicbookrental.ui.components.commonComponents.LoadingScreen
import com.example.comicbookrental.ui.components.commonComponents.LocalTopBarState
import com.example.comicbookrental.ui.components.commonComponents.TopBarState
import com.example.comicbookrental.ui.components.commonComponents.PanelRushBottomBar
import com.example.comicbookrental.ui.components.commonComponents.PanelRushTopBar
import com.example.comicbookrental.ui.components.commonComponents.SecondaryTopBar
import com.example.comicbookrental.ui.screens.cart.CartScreen
import com.example.comicbookrental.ui.screens.checkout.CheckoutScreen
import com.example.comicbookrental.ui.screens.profile.ProfileScreen
import com.example.comicbookrental.ui.screens.profile_detail.ProfileDetailScreen
import com.example.comicbookrental.ui.screens.search.SearchRoute
import com.example.comicbookrental.ui.screens.onboarding.OnboardingScreen
import com.example.comicbookrental.utils.StoreManager
import kotlinx.coroutines.delay

@Composable
fun AppNavHost()
{
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val checkoutRepository = remember { CheckoutRepositoryImpl() }
    val context = LocalContext.current
    val storeManager = remember { StoreManager(context) }

    var showGlobalLoading by remember { mutableStateOf(false) }
    var previousDestinationRoute by remember { mutableStateOf<String?>(null) }
    val topBarState = remember { mutableStateOf(TopBarState()) }

    LaunchedEffect(currentDestination) {
        val currentRoute = currentDestination?.route
        if (currentRoute != null && previousDestinationRoute != null && currentRoute != previousDestinationRoute)
        {
            showGlobalLoading = true
            delay(1500)
            showGlobalLoading = false
        }
        previousDestinationRoute = currentRoute
    }

    val startGraph = remember {
        if (!storeManager.isOnboardingCompleted())
        {
            OnboardingRoute
        }
        else if (storeManager.isLoggedIn())
        {
            if (storeManager.getUserProfile().role.isAdmin) AdminGraph else CatalogGraph
        }
        else
        {
            AuthGraph
        }
    }

    val tabs = listOf(
        NavigationTab("Home", HomeRoute, Icons.Default.Home),
        NavigationTab("Search", SearchRoute, Icons.Default.Search),
        NavigationTab("Rental", MyRentalsRoute, Icons.Default.Book),
        NavigationTab("Profile", ProfileRoute, Icons.Default.Person),
    )

    val showBottomBar = currentDestination?.hierarchy?.any { dest ->
        dest.hasRoute<HomeRoute>() ||
                dest.hasRoute<MyRentalsRoute>() ||
                dest.hasRoute<ProfileRoute>() ||
                dest.hasRoute<SearchRoute>()
    } == true

    val title = when
    {
        currentDestination?.hasRoute<ComicDetailRoute>() == true -> "COMIC DETAIL"
        currentDestination?.hasRoute<ProfileDetailRoute>() == true -> "PROFILE EDIT"
        currentDestination?.hasRoute<WishlistRoute>() == true -> "MY WISHLIST"
        currentDestination?.hasRoute<NotificationsRoute>() == true -> "NOTIFICATIONS"
        currentDestination?.hasRoute<SettingsRoute>() == true -> "SETTINGS"
        else -> ""
    }

    val isShowSecondaryTopBar = currentDestination?.hierarchy?.any { dest ->
        dest.hasRoute<ComicDetailRoute>() ||
                dest.hasRoute<ProfileDetailRoute>() ||
                dest.hasRoute<WishlistRoute>() ||
                dest.hasRoute<NotificationsRoute>() ||
                dest.hasRoute<SettingsRoute>()
    } == true

    CompositionLocalProvider(LocalTopBarState provides topBarState) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    if (showBottomBar)
                    {
                        Box(modifier = Modifier.statusBarsPadding()) {
                            PanelRushTopBar(
                                onMenuClick = {
                                    val currentRoute = currentDestination?.route
                                    if (currentRoute != HomeRoute.toString())
                                    {
                                        navController.navigate(HomeRoute)
                                    }
                                    else
                                    {
                                        Log.d("Topbar", "Current is home route")
                                    }
                                },
                                onNotificationsClick = { navController.navigate(NotificationsRoute) },
                                onNavigateToCartClick = { navController.navigate(CartRoute) }
                            )
                        }
                    }
                    else if (isShowSecondaryTopBar)
                    {
                        Box(modifier = Modifier.statusBarsPadding()) {
                            SecondaryTopBar(
                                title = title,
                                onBackClick = { navController.popBackStack() },
                                onCartClick = { navController.navigate(CartRoute) },
                                isShowHeart = topBarState.value.isShowHeart,
                                isInterested = topBarState.value.isInterested,
                                onInterestedClick = topBarState.value.onInterestedClick,
                                isShowCart = currentDestination?.hasRoute<SettingsRoute>() != true && currentDestination?.hasRoute<NotificationsRoute>() != true
                            )
                        }
                    }
                },
                bottomBar = {
                    if (showBottomBar)
                    {
                        Box(modifier = Modifier.navigationBarsPadding()) {
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
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = startGraph,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable<OnboardingRoute> {
                        OnboardingScreen(
                            onComplete = {
                                storeManager.setOnboardingCompleted(true)
                                navController.navigate(if (storeManager.isLoggedIn()) CatalogGraph else AuthGraph) {
                                    popUpTo(OnboardingRoute) { inclusive = true }
                                }
                            }
                        )
                    }

                    authGraph(navController)
                    catalogGraph(navController)
                    rentalGraph(
                        navController = navController,
                        checkoutRepository = checkoutRepository
                    )
                    profileExtensionsGraph(navController)
                    adminGraph(navController) {
                        storeManager.logOut()
                        navController.navigate(AuthGraph) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    composable<SearchRoute> {
                        SearchRoute(
                            onComicClick = { comicId ->
                                navController.navigate(ComicDetailRoute(comicId.toString()))
                            }
                        )
                    }

                    composable<CartRoute> {
                        CartScreen(
                            onBackClick = { navController.popBackStack() },
                            onCheckoutClick = { cartItems ->
                                checkoutRepository.prepareCartCheckout(cartItems)
                                navController.navigate(CheckoutRoute)
                            }
                        )
                    }

                    composable<CheckoutRoute> {
                        CheckoutScreen(
                            onBackClick = { navController.popBackStack() },
                            onViewRentalsClick = {
                                navController.navigate(MyRentalsRoute) {
                                    popUpTo(CheckoutRoute) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable<ProfileRoute> {
                        ProfileScreen(
                            onLogOut = {
                                navController.navigate(AuthGraph) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onProfileDetailClick = {
                                navController.navigate(
                                    ProfileDetailRoute
                                )
                            },
                            onCartClick = { navController.navigate(CartRoute) },
                            onWishlistClick = { navController.navigate(WishlistRoute) },
                            onHistoryClick = { navController.navigate(MyRentalsRoute) },
                            onSettingsClick = { navController.navigate(SettingsRoute) },
                            onNotificationsClick = {
                                navController.navigate(
                                    NotificationsRoute
                                )
                            }
                        )
                    }

                    composable<ProfileDetailRoute> {
                        ProfileDetailScreen(
                            onBackClick = { navController.popBackStack() },
                            onCartClick = { navController.navigate(CartRoute) }
                        )
                    }
                }
            }
            if (showGlobalLoading)
            {
                LoadingScreen()
            }
        }
    }
}
