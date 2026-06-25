package com.example.comicbookrental.ui.components.notificationComponents

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.data.entities.AppNotification
import com.example.comicbookrental.data.entities.AppNotificationType
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.components.commonComponents.rememberComicPressState
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun NotificationItem(
    notification: AppNotification,
    onClick: () -> Unit
)
{
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    val backgroundColor = MaterialTheme.colorScheme.background

    val icon: ImageVector = when (notification.type)
    {
        AppNotificationType.RENTAL_REMINDER -> Icons.Filled.ShoppingCart
        AppNotificationType.NEW_RELEASE -> Icons.Filled.Star
        AppNotificationType.PROMO -> Icons.Filled.Notifications
        AppNotificationType.RECOMMENDATION -> Icons.Filled.Info
    }

    val interactionSource = remember { MutableInteractionSource() }
    val press = rememberComicPressState(interactionSource)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = press.translation, y = press.translation)
            .comicHardShadow(shape = shape, offset = press.shadowOffset, color = ink)
            .clip(shape)
            .background(backgroundColor)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(Dimens.Spacing.Margin)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(Dimens.Radius.Button))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .border(width = 2.dp, color = ink, shape = RoundedCornerShape(Dimens.Radius.Button)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(Dimens.Spacing.Margin))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title.uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = Anton,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (!notification.isRead)
                    {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.error)
                                .border(width = 2.dp, color = ink, shape = RoundedCornerShape(50))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = DateUtils.getRelativeTimeSpanString(notification.timestamp).toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontFamily = Anton,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}