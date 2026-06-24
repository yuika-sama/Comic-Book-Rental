package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.comicbookrental.ui.navigation.NavigationTab
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.HankenGrotesk

@Composable
fun PanelRushBottomBar(
    tabs: List<NavigationTab<out Any>>,
    currentDestination: NavDestination?,
    onTabClick: (NavigationTab<out Any>) -> Unit
)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        HorizontalDivider(color = InkBlack, thickness = 3.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.hasRoute(tab.route::class)
                } == true

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
                        .drawBehind {
                            if (isSelected)
                            {
                                // Left border
                                drawLine(
                                    color = InkBlack,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = 3.dp.toPx()
                                )
                                // Right border
                                drawLine(
                                    color = InkBlack,
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 3.dp.toPx()
                                )
                            }
                        }
                        .clickable {
                            onTabClick(tab)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (isSelected) Color.White else InkBlack,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tab.label.uppercase(),
                            color = if (isSelected) Color.White else InkBlack,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = HankenGrotesk,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}
