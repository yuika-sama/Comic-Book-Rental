package com.example.comicbookrental.ui.components.onboardingComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary

@Composable
fun ImmersiveReadingPage(onNext: () -> Unit, onSkip: () -> Unit)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PANEL RUSH",
                fontFamily = Anton,
                fontSize = 24.sp,
                color = Primary,
                modifier = Modifier.rotate(-5f)
            )
            Text(
                text = "SKIP",
                fontFamily = Anton,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onSkip() }
            )
        }

        // Image Frame
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .comicHardShadow(
                    shape = RoundedCornerShape(8.dp),
                    offset = 8.dp,
                    color = InkBlack
                )
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .border(3.dp, InkBlack, RoundedCornerShape(8.dp))
        ) {
            // Simulated content inside the frame
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0E0E0))
            ) {
                // Top bar of reader
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(InkBlack)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(Primary)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "CH. 42: THE BREACH",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Panel Zoom box
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.DarkGray)
                ) {
                    // Zoom active badge
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
//                            .shape(RoundedCornerShape(50))
                            .background(Color.Blue)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = "Zoom",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "PANEL ZOOM ACTIVE",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Infinite Scroll box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.Gray)
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
//                            .RoundedCornerShape(50)
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mouse,
                            contentDescription = "Scroll",
                            tint = Primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "INFINITE SCROLL",
                            color = InkBlack,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Step Badge
        Box(
            modifier = Modifier
//                .RoundedCornerShape(50)
                .border(2.dp, InkBlack, RoundedCornerShape(50))
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = "STEP 2 OF 4",
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Text Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "IMMERSIVE",
                fontFamily = Anton,
                fontSize = 42.sp,
                lineHeight = 42.sp,
                color = InkBlack
            )
            Text(
                text = "READING",
                fontFamily = Anton,
                fontSize = 42.sp,
                lineHeight = 42.sp,
                color = Primary,
                modifier = Modifier.rotate(-3f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Experience stories like never before. Dive into fluid Infinite Scroll or use Panel Zoom to catch every single ink stroke.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Feature Cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Fluid Flow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Primary, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Primary)
                        .border(2.dp, InkBlack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Book, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("FLUID FLOW", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        "Seamless vertical scrolling designed for modern mobile displays.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Smart Zoom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Primary, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Blue)
                        .border(2.dp, InkBlack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ZoomOutMap, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("SMART ZOOM", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        "Tap any panel to center and enlarge high-definition details.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // NEXT CHAPTER Button
        BrutalistButton(
            text = "NEXT CHAPTER →",
            onClick = onNext,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Page Indicator
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .border(2.dp, InkBlack)
            )
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(6.dp)
                    .background(Primary)
                    .border(1.dp, InkBlack)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .border(2.dp, InkBlack)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .border(2.dp, InkBlack)
            )
        }
    }
}