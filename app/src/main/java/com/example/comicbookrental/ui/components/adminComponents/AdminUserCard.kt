package com.example.comicbookrental.ui.components.adminComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.AdminUser
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.Error
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun AdminUserCard(
    user: AdminUser,
    onBan: () -> Unit,
    onUnban: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .padding(Dimens.Spacing.ContentSpacing),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(Dimens.Radius.Inner))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .border(Dimens.Border.Hairline, ink, RoundedCornerShape(Dimens.Radius.Inner)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(modifier = Modifier.width(Dimens.Spacing.ContentSpacing))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimens.Spacing.StackSm))
                StatusChip(user = user, ink = ink)
            }
        }

        if (!user.isAdmin) {
            if (user.isBanned) {
                ComicButton(
                    text = "Unban",
                    onClick = onUnban,
                    variant = ComicButtonVariant.Secondary,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                ComicButton(
                    text = "Ban",
                    onClick = onBan,
                    variant = ComicButtonVariant.Secondary,
                    contentColor = Error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StatusChip(user: AdminUser, ink: Color) {
    val (label, color) = when {
        user.isAdmin -> "ADMIN" to MaterialTheme.colorScheme.primary
        user.isBanned -> "BANNED" to Error
        else -> "ACTIVE" to MaterialTheme.extendedColors.success
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.Radius.Sm))
            .background(color.copy(alpha = 0.15f))
            .border(Dimens.Border.Hairline, color, RoundedCornerShape(Dimens.Radius.Sm))
            .padding(horizontal = Dimens.Spacing.StackSm, vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
