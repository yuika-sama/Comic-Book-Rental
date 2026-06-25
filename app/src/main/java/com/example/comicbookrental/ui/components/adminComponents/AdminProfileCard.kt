package com.example.comicbookrental.ui.components.adminComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.data.entities.User
import com.example.comicbookrental.ui.components.profileComponents.AvatarWithBadge
import com.example.comicbookrental.ui.components.profileComponents.SolidShadowBox
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun AdminProfileCard(profile: User?) {
    SolidShadowBox(
        modifier = Modifier.fillMaxWidth(),
        shadowColor = MaterialTheme.colorScheme.primary,
        offsetX = 6.dp,
        offsetY = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarWithBadge(avatarUrl = profile?.avatarUrl)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = profile?.heroName ?: "LOADING...",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ADMINISTRATOR",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.Radius.Sm))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    .border(
                        Dimens.Border.Hairline,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(Dimens.Radius.Sm)
                    )
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = profile?.email ?: "",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
