package com.example.comicbookrental.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.components.FacebookLoginButton
import com.example.comicbookrental.ui.components.GoogleLoginButton
import com.example.comicbookrental.ui.components.BrutalistButton
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.HankenGrotesk

@Composable
fun RegisterScreen()
{
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isAgreed by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PANEL RUSH",
                fontFamily = Anton,
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
                .height(3.dp)
                .background(Color.Black)
                .padding(top =  4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .offset(x = 2.dp, y = 2.dp)
                .background(Color.Gray.copy(alpha = 0.5f))
        )
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "JOIN THE",
                fontFamily = Anton,
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier =  Modifier.offset(y = 12.dp)
            )

            Box(modifier = Modifier.padding(end = 6.dp, bottom = 6.dp)) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 6.dp, y = 6.dp)
                        .background(Color.Black)
                )
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondary)
                        .border(3.dp, Color.Black)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "SQUAD",
                        fontFamily = Anton,
                        fontSize = 48.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp, bottom = 8.dp)
            ){
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = 8.dp, y = 8.dp)
                        .background(Color.Black)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .offset(y = 4.dp)
                        .border(width =  2.dp, color = Color.Black)
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    BrutalistTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "FULL NAME",
                        placeholder = "WADE WILSON",
                        leadingIcon = Icons.Outlined.Person
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    BrutalistTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "EMAIL ADDRESS",
                        placeholder = "HERO@PANELRUSH.COM",
                        leadingIcon = Icons.Outlined.Email
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    BrutalistTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "SECRET IDENTITY (PASSWORD)",
                        placeholder = "••••••••",
                        leadingIcon = Icons.Outlined.Lock,
                        isPassword = true,
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = "Show Password",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    BrutalistTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "CONFIRM PASSWORD",
                        placeholder = "••••••••",
                        leadingIcon = Icons.Outlined.Security,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier =  Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = isAgreed,
                            onCheckedChange = {isAgreed = it},
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = Color.Black,
                                checkmarkColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = buildAnnotatedString {
                                append("I AGREE TO THE ")
                                withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                                    append("CRITICAL ALLIANCE TERMS")
                                }
                                append(" AND PRIVACY PROTOCOLS.")
                            },
                            fontFamily = HankenGrotesk,
                            fontSize = 12.sp,
                            color = Color.Black,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    BrutalistButton(
                        text = "SIGN UP",
                        onClick = { Log.d("Register", "Registered") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    HorizontalDivider(Modifier, thickness = 1.dp, color = Color.LightGray)

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "ALREADY A MEMBER? ",
                            fontFamily = HankenGrotesk,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "LOGIN HERE",
                            fontFamily = HankenGrotesk,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue,
                            modifier = Modifier.clickable { /* Điều hướng về Login */ }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                GoogleLoginButton(
                    onClick = {},
                    modifier = Modifier
                )
                FacebookLoginButton(
                    onClick = {},
                    modifier = Modifier
                )
            }
        }
    }
}