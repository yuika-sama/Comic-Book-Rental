package com.example.comicbookrental.ui.components.profileComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField

@Composable
fun ProfileInformationCard(
    isEditing: Boolean = false,
    realName: String = "",
    phone: String = "",
    region: String = "",
    onRealNameChange: (String) -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
    onRegionChange: (String) -> Unit = {}
) {
    NeoBox(modifier = Modifier.fillMaxWidth(), backgroundColor = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Blue)
                Spacer(modifier = Modifier.width(8.dp))
                Text("INFORMATION", fontWeight = FontWeight.Black, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            ContactField("NAME", realName)
            Spacer(modifier = Modifier.height(16.dp))
            ContactField("COMM CHANNEL (PHONE)", phone)
            Spacer(modifier = Modifier.height(16.dp))
            ContactField("SECTOR (REGION)", region)
        }
    }
}