package com.example.comicbookrental.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.data.models.UserProfile

@Composable
fun ProfileCard(profile: UserProfile?) {
    SolidShadowBox(
        modifier = Modifier.fillMaxWidth(),
        shadowColor = Color.Blue,
        offsetX = 6.dp,
        offsetY = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarWithBadge()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = profile?.heroName ?: "LOADING...",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Text(
                text = "COLLECTOR RANK: ${profile?.rank ?: "..."}",
                color = Color.Blue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(Modifier, thickness = 1.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatItem(profile?.rentedCount?.toString() ?: "0", "RENTED")
                HorizontalDivider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp), thickness = DividerDefaults.Thickness, color = Color.Black
                )
                StatItem(profile?.activeCount?.toString() ?: "0", "ACTIVE")
                HorizontalDivider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp), thickness = DividerDefaults.Thickness, color = Color.Black
                )
                StatItem(profile?.rating?.toString() ?: "0.0", "RATING")
            }
        }
    }
}