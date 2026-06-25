package com.example.comicbookrental.ui.components.onboardingComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary

@Composable
fun ManageLegacyPage(onNext: () -> Unit)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = InkBlack,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "PANEL RUSH",
                fontFamily = Anton,
                fontSize = 20.sp,
                color = Primary
            )
            Box(
                modifier = Modifier
                    .background(Color.Blue)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    "STEP 03/03",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = InkBlack,
                modifier = Modifier.size(24.dp)
            )
        }

        HorizontalDivider(thickness = 3.dp, color = InkBlack)

        Column(modifier = Modifier.padding(16.dp)) {
            // Header Text
            Text(
                text = "MANAGE YOUR",
                fontFamily = Anton,
                fontSize = 36.sp,
                lineHeight = 36.sp,
                color = InkBlack,
                modifier = Modifier.rotate(-3f)
            )
            Text(
                text = "LEGACY",
                fontFamily = Anton,
                fontSize = 36.sp,
                lineHeight = 36.sp,
                color = Primary,
                modifier = Modifier.rotate(-3f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(48.dp)
                        .background(Color.Blue)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Every reader has a story. Track your conquests, manage your active Issues, and build a collection that ripples through time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Active Missions Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .comicHardShadow(
                        shape = RoundedCornerShape(0.dp),
                        offset = 8.dp,
                        color = InkBlack
                    )
                    .background(Color.White)
                    .border(3.dp, InkBlack)
                    .padding(16.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Timer,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ACTIVE MISSIONS", fontFamily = Anton, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Mission 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, InkBlack)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.DarkGray)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("CYBER-SLAYER #42", fontFamily = Anton, fontSize = 16.sp)
                            Text("Issue: The Neon Reckoning", fontSize = 10.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(4.dp))
                            // Progress bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .border(1.dp, InkBlack)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                        .fillMaxHeight()
                                        .background(Primary)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color.Red)
                                .padding(4.dp)
                        ) {
                            Text(
                                "3 DAYS\nLEFT",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mission 2
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, InkBlack)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("SHADOW REALM VOL. 1", fontFamily = Anton, fontSize = 16.sp)
                            Text(
                                "Issue: Descent Into Darkness",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            // Progress bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .border(1.dp, InkBlack)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.1f)
                                        .fillMaxHeight()
                                        .background(Color.Blue)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(Color.Blue)
                                .padding(4.dp)
                        ) {
                            Text(
                                "NEW\nMISSION",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Combat Stats Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .comicHardShadow(
                        shape = RoundedCornerShape(0.dp),
                        offset = 8.dp,
                        color = InkBlack
                    )
                    .background(Primary)
                    .border(3.dp, InkBlack)
                    .padding(16.dp)
            ) {
                Column {
                    Text("COMBAT STATS", fontFamily = Anton, fontSize = 20.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Legacy Loaded Badge overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .border(2.dp, InkBlack)
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Primary), contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.CloudSync,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("LEGACY LOADED", fontFamily = Anton, fontSize = 12.sp)
                                Text(
                                    "Profile synchronized with central hub.",
                                    fontSize = 8.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("TOTAL READS", fontSize = 10.sp, color = Color.White)
                    Text("1,200+", fontFamily = Anton, fontSize = 32.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("LEGACY LEVEL", fontSize = 10.sp, color = Color.White)
                    Text("HEROIC", fontFamily = Anton, fontSize = 28.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("ACHIEVEMENT RANK", fontSize = 10.sp, color = Color.White)
                    Row {
                        repeat(5) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(end = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ComicButton(
                        text = "VIEW RECORDS",
                        onClick = { },
                        variant = ComicButtonVariant.Secondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .comicHardShadow(
                        shape = RoundedCornerShape(0.dp),
                        offset = 8.dp,
                        color = InkBlack
                    )
                    .background(Color(0xFFFFE0D0))
                    .border(3.dp, InkBlack)
                    .padding(16.dp)
            ) {
                Column {
                    Text("SYSTEM ALERT", fontFamily = Anton, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "You have 2 items reaching their final chapter soon. Avoid legacy penalties by completing your missions.",
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "RETURN WINDOW CLOSES IN 48H",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .comicHardShadow(
                        shape = RoundedCornerShape(0.dp),
                        offset = 8.dp,
                        color = InkBlack
                    )
                    .background(Color.White)
                    .border(3.dp, InkBlack)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
//                            .shape(RoundedCornerShape(50))
                            .background(Color.Black)
                            .border(2.dp, Color.Cyan, RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("AGENT_X7", fontFamily = Anton, fontSize = 18.sp)
                        Text("Profile Tier: Vanguard", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "EDIT IDENTITY",
                            fontSize = 10.sp,
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {})
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom Action
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("READY TO ENTER THE FRAY?", fontFamily = Anton, fontSize = 20.sp)
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(2.dp)
                        .background(Primary)
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                BrutalistButton(
                    text = "GET STARTED",
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
//                    trailingIcon = Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = Color.White)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "FINAL PHASE SEQUENCE INITIATED",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}