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
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary

@Composable
fun DiscoverSagaPage(onNext: () -> Unit, onSkip: () -> Unit)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, end = 8.dp)
                    .comicHardShadow(
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
                        offset = DpOffset(8.dp, 8.dp),
                        color = InkBlack
                    )
                    .clip(RoundedCornerShape(4.dp))
                    .background(InkBlack)
                    .border(
                        3.dp,
                        InkBlack,
                        RoundedCornerShape(4.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2A1C16))
                )
            }

            // NEW Badge
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, start = 0.dp)
                    .rotate(-15f)
                    .background(Primary)
                    .border(2.dp, InkBlack)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "NEW!",
                    fontFamily = com.example.comicbookrental.ui.theme.Anton,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            // SKIP Button
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 8.dp)
                    .clickable { onSkip() }
                    .background(Color.White)
                    .border(2.dp, InkBlack)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "SKIP",
                    fontFamily = Anton,
                    fontSize = 14.sp
                )
            }
        }

        // Text Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "DISCOVER YOUR",
                fontFamily = Anton,
                fontSize = 42.sp,
                lineHeight = 42.sp,
                color = InkBlack
            )
            Text(
                text = "SAGA",
                fontFamily = Anton,
                fontSize = 56.sp,
                lineHeight = 56.sp,
                color = Primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome to the ultimate comic sanctuary. Rent thousands of legendary titles and start your reading adventure today.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cards Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // BROWSE Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(InkBlack.copy(alpha = 0.05f))
                    .border(2.dp, InkBlack)
                    .padding(16.dp)
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Browse",
                        tint = Color.Blue,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "BROWSE",
                        fontFamily = Anton,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Explore massive archives by genre.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // RENT Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Primary.copy(alpha = 0.2f))
                    .border(2.dp, InkBlack)
                    .padding(16.dp)
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Outlined.Book,
                        contentDescription = "Rent",
                        tint = Primary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "RENT",
                        fontFamily = Anton,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Secure issues for a week of reading.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // NEXT Button
        BrutalistButton(
            text = "NEXT →",
            onClick = onNext,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Page Indicator
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(6.dp)
                    .background(InkBlack)
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