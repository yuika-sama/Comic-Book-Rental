package com.example.comicbookrental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import com.example.comicbookrental.services.NotificationScheduler

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted)
        {
            NotificationScheduler.scheduleRepeatingNotifications(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        else
        {
            NotificationScheduler.scheduleRepeatingNotifications(this)
        }

        setContent {
            ComicBookRentalTheme {
                AppNavHost()
            }
        }
    }
}
