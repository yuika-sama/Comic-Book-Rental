package com.example.comicbookrental.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.notificationComponents.NotificationItem
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
)
{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (uiState.notifications.isEmpty())
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "YOU HAVE NO NEW NOTIFICATIONS.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = Anton,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
        else
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = Dimens.Spacing.Margin,
                    vertical = Dimens.Spacing.StackLg
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd)
            ) {
                items(items = uiState.notifications, key = { it.id }) { notification ->
                    NotificationItem(
                        notification = notification,
                        onClick = {
                            if (!notification.isRead)
                            {
                                viewModel.markAsRead(notification.id)
                            }
                        }
                    )
                }
            }
        }
    }
}
