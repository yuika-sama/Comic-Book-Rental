package com.example.comicbookrental.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.AuthIconBox
import com.example.comicbookrental.ui.components.AuthTopHeader
import com.example.comicbookrental.ui.components.BrutalistButton
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.components.PasswordStrengthEvaluator
import com.example.comicbookrental.ui.components.SecurityAlert
import com.example.comicbookrental.ui.theme.Background

@Composable
fun ResetPasswordScreen()
{
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            AuthIconBox(icon = Icons.Default.Lock)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "RESET PASSWORD",
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Black)
            )
            Text(
                text = "Lock your vault with a high-security access key.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "NEW PASSWORD",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
            BrutalistTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "New password",
                placeholder = "Enter your new password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )
            PasswordStrengthEvaluator(strength = 3, label = "Almost invincible")

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "CONFIRM NEW PASSWORD",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
            BrutalistTextField(
                value = confirmNewPassword,
                onValueChange = { confirmNewPassword = it },
                label = "Confirm new password",
                placeholder = "Enter your confirm password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            SecurityAlert(
                message = "Updating your password will sign you out of all other active comic sessions on other devices."
            )

            Spacer(modifier = Modifier.height(40.dp))

            BrutalistButton(
                text = "UPDATE PASSWORD",
                onClick = {
                    Log.d("Update Password", "Password updated")
                },
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* Handle cancel */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(2.dp, Color.Black),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text("CANCEL RECOVERY", fontWeight = FontWeight.Black, fontSize = 18.sp)
            }
        }
    }
}