package com.example.comicbookrental.ui.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.BrutalistButton
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.theme.AntonFont
import com.example.comicbookrental.ui.theme.HankenGroteskFont

@Composable
fun ForgotPasswordScreen()
{
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
//            .padding(bottom = 32.dp)
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PANEL RUSH",
                fontFamily = AntonFont,
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* Xử lý đóng/back */ }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .weight(1f)
        ){
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp)
                    .offset(x = 8.dp, y = 8.dp)

                    .background(Color.Black)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp)
                    .background(Color.White)
                    .border(width = 3.dp, color = Color.Black)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                   text = "RECOVER YOUR ACCOUNT",
                   fontFamily = AntonFont,
                   fontSize = 28.sp,
                   color = Color.Black,
                   textAlign = TextAlign.Center
                )
                Text(
                    text = buildAnnotatedString {
                        append("Lost your way in the Multiverse? Enter your email and we'll send an ")
                        withStyle(
                            style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
                        ){
                            append("RESCUE OTP ")
                        }
                        append("to your inbox")
                    },
                    fontFamily = HankenGroteskFont,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                BrutalistTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "SECRET IDENTITY (EMAIL)",
                    placeholder = "superhero@panelrush.com",
                    leadingIcon = Icons.Outlined.Email
                )

                Spacer(modifier = Modifier.height(24.dp))

                BrutalistButton(
                    text = "SEND OTP",
                    onClick = {},
                    modifier =  Modifier.fillMaxWidth()
                )

                Spacer(modifier =  Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable{}
                ) {
                    Text(text = "← ", color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp)
                    Text(
                        text = "BACK TO LOGIN",
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = HankenGroteskFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            // Icon top bar
            Box(
                modifier = Modifier.align (Alignment.TopCenter)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .offset(x = 6.dp, y = 6.dp)
                        .background(Color.Black)
                )

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(3.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Outlined.Replay,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Box(
            modifier =  Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 12.dp)
        ){
            Text(
                text = "THE FIGHT • NEVER LOSE YOUR PROGRESS • SECURE YOUR COLLECTION • ",
                color = Color.White,
                fontFamily = AntonFont,
                fontSize = 18.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    velocity = 30.dp
                )
            )
        }
    }
}