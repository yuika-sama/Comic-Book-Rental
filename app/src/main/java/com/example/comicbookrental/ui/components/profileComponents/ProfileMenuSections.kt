package com.example.comicbookrental.ui.components.profileComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.InkBlack

@Composable
fun ProfileMenuSection(
    onProfileDetailClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    cartBadgeCount: Int = 3
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "ACCOUNT COMMANDS",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HorizontalDivider(Modifier, thickness = 1.dp, color = InkBlack)

        ProfileMenuItem(Icons.Default.Person, "PROFILE DETAIL", onClick = onProfileDetailClick)
        ProfileMenuItem(Icons.Default.ShoppingCart, "MY CART", badge = if (cartBadgeCount > 0) cartBadgeCount else null, onClick = onCartClick)
        ProfileMenuItem(Icons.Default.FavoriteBorder, "INTERESTED (WISHLIST)", onClick = onWishlistClick)
        ProfileMenuItem(Icons.AutoMirrored.Filled.List, "RENTED HISTORY", onClick = onHistoryClick)
        ProfileMenuItem(Icons.Default.Notifications, "NOTIFICATIONS", onClick = onNotificationsClick)
        ProfileMenuItem(Icons.Default.Settings, "SETTINGS", onClick = onSettingsClick)
    }
}