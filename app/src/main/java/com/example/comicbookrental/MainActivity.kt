package com.example.comicbookrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.ui.screens.reader_screen.ReaderScreen
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.screens.reset_password.ResetPasswordScreen
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
//                AppNavHost()
//                ResetPasswordScreen(email = AuthMockData.VALID_EMAIL)
                ReaderScreen(
                    rentalId = 3,
                    onBackClick = {},
                    onExtendRentalClick = {}
                )
            }
        }
    }
}