package com.example.comicbookrental.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun OtpCard(){
    Box(
        modifier =  Modifier
            .fillMaxWidth()
            .offset(x = 8.dp, y = 8.dp)
            .background(Color.Black)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (-8).dp, y = (-8).dp)
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, Color.Black)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "VERIFY YOUR EMAIL",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We've sent an OTP to your email. Input it below",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            OtpInputRow(otpCount = 5)

            Spacer(modifier = Modifier.height(24.dp))

            BrutalistButton(
                text = "VERIFY",
                onClick = {
                    Log.d("Verify", "Verified")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row() {
                Text(
                    text = "Didn't get it? ",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = "RESEND CODE",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable{
                        Log.d("Resend", "Resend")
                    }
                )
            }



            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Wait 54 seconds before resending.",
                fontSize = 12.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
        }
    }
}