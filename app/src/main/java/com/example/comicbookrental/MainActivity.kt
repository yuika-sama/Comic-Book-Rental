package com.example.comicbookrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.ui.screens.forgot_password.ForgotPasswordScreen
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.screens.login.LoginScreen
import com.example.comicbookrental.ui.screens.register.RegisterScreen
import com.example.comicbookrental.ui.screens.reset_password.ResetPasswordScreen
import com.example.comicbookrental.ui.screens.verify_otp.VerifyOtpScreen
import com.example.comicbookrental.ui.screens.DesignTestPreview
import com.example.comicbookrental.ui.screens.ForgotPasswordScreen
import com.example.comicbookrental.ui.screens.cart.CartScreen
import com.example.comicbookrental.ui.screens.rentals.MyRentalsScreen
import com.example.comicbookrental.ui.screens.home.HomeRoute
import com.example.comicbookrental.ui.screens.home.HomeScreen
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
                ResetPasswordScreen(email = AuthMockData.VALID_EMAIL)
            }
        }
    }
}