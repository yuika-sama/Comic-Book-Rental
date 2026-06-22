package com.example.comicbookrental.ui.screens

import android.media.FaceDetector
import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.components.FacebookLoginButton
import com.example.comicbookrental.ui.components.GoogleLoginButton
import com.example.comicbookrental.ui.components.LoginButton
import com.example.comicbookrental.ui.theme.AntonFont
import com.example.comicbookrental.ui.theme.HankenGroteskFont

@Composable
fun LoginScreen()
{
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 8.dp, y = 8.dp)
                    .background(Color.Black)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .border(width = 3.dp, color = Color.Black)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PANEL RUSH",
                    fontFamily = AntonFont,
                    fontSize = 56.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                Text(
                    text = "YOUR NEXT ISSUE AWAITS",
                    fontFamily = HankenGroteskFont,
                    letterSpacing = 2.sp,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(48.dp))

                BrutalistTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "EMAIL ADDRESS",
                    placeholder = "comic.rent@gmail.com",
                    leadingIcon = Icons.Outlined.Email
                )
                Spacer(modifier = Modifier.height(24.dp))
                BrutalistTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "PASSWORD",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    trailingContent = {
                        Text(
                            text = "FORGOT?",
                            fontFamily = HankenGroteskFont,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                LoginButton(
                    onClick = { Log.d("Login", "Logged In") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 2.dp,
                        color = Color.Black
                    )
                    Text(
                        text = " OR CONNECT WITH ",
                        fontFamily = HankenGroteskFont,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 2.dp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GoogleLoginButton(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                    FacebookLoginButton(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                }

//                Spacer(modifier = Modifier.weight(1f))

                Row(modifier = Modifier.padding(bottom = 24.dp)) {
                    Text(
                        text = "New to the collection? ",
                        fontFamily = HankenGroteskFont,
                        color = Color.Black
                    )
                    Text(
                        text = "JOIN TODAY",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HankenGroteskFont,
                        modifier = Modifier.clickable { /* Navigate to register */ }
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun LoginPreview()
{
    LoginScreen()
}