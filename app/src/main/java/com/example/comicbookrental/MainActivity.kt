package com.example.comicbookrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.comicbookrental.ui.navigation.CatalogGraph
import com.example.comicbookrental.ui.navigation.catalogGraph
import com.example.comicbookrental.ui.screens.search.SearchRoute
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComicBookRentalTheme {
                SearchRoute(onComicClick = {})

                // val navController = rememberNavController()
                // NavHost(
                //     navController = navController,
                //     startDestination = CatalogGraph,
                // ) {
                //     catalogGraph(navController)
                // }
            }
        }
    }
}
