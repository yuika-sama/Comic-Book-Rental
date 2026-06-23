package com.example.comicbookrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.screens.auth.ForgotPasswordScreen
import com.example.comicbookrental.ui.screens.auth.RegisterScreen
import com.example.comicbookrental.ui.screens.auth.ResetPasswordScreen
import com.example.comicbookrental.ui.screens.auth.VerifyOtpScreen
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
                ResetPasswordScreen()
            }
        }
    }
}