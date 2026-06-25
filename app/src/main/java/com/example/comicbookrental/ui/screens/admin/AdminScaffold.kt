package com.example.comicbookrental.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.comicbookrental.ui.components.commonComponents.PanelRushBottomBar
import com.example.comicbookrental.ui.navigation.AdminDashboardRoute
import com.example.comicbookrental.ui.navigation.AdminGraph
import com.example.comicbookrental.ui.navigation.AdminManageComicsRoute
import com.example.comicbookrental.ui.navigation.AdminManageUsersRoute
import com.example.comicbookrental.ui.navigation.AdminProfileRoute
import com.example.comicbookrental.ui.navigation.NavigationTab
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack


@Composable
fun AdminScaffold(
    navController: NavHostController,
    content: @Composable (androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    val tabs = listOf(
        NavigationTab("Dashboard", AdminDashboardRoute, Icons.Default.Dashboard),
        NavigationTab("Users", AdminManageUsersRoute, Icons.Default.Group),
        NavigationTab("Comics", AdminManageComicsRoute, Icons.Default.MenuBook),
        NavigationTab("Profile", AdminProfileRoute, Icons.Default.Person),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            AdminTopBar(title = currentDestination.adminTitle())
        },
        bottomBar = {
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
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
private fun AdminTopBar(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "ADMIN",
                color = MaterialTheme.colorScheme.primary,
                fontFamily = Anton,
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
            Text(
                text = title,
                color = InkBlack,
                fontFamily = Anton,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp
            )
        }
        HorizontalDivider(color = InkBlack, thickness = 3.dp)
    }
}

private fun NavDestination?.adminTitle(): String = when {
    this == null -> "DASHBOARD"
    hierarchy.any { it.hasRoute<AdminManageUsersRoute>() } -> "MANAGE USERS"
    hierarchy.any { it.hasRoute<AdminManageComicsRoute>() } -> "MANAGE COMICS"
    hierarchy.any { it.hasRoute<AdminProfileRoute>() } -> "PROFILE"
    else -> "DASHBOARD"
}
