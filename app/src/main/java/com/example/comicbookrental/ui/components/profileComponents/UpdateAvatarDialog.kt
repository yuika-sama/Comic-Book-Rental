package com.example.comicbookrental.ui.components.profileComponents

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField

@Composable
fun UpdateAvatarDialog(
    onUploadLocalAvatar: (Context, String) -> Unit,
    onUploadUrlAvatar: (String) -> Unit,
    onCancelUpdateAvatar: () -> Unit,
    editAvatarUrl: String,
    onSaveAvatar: () -> Unit
)
{
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onUploadLocalAvatar(context, it.toString())
        }
    }
    AlertDialog(
        onDismissRequest = { onCancelUpdateAvatar() },
        title = { Text("UPDATE AVATAR", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                AsyncImage(
                    model = editAvatarUrl.ifEmpty { "https://via.placeholder.com/150" },
                    contentDescription = "Avatar Preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.LightGray, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                ComicButton(
                    text = "SELECT FROM DEVICE",
                    onClick = { photoPickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("OR PASTE IMAGE URL:", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                BrutalistTextField(
                    value = editAvatarUrl,
                    onValueChange = onUploadUrlAvatar,
                    label = "IMAGE URL",
                    placeholder = "https://...",
                    leadingIcon = Icons.Default.TextFields
                )
            }
        },
        confirmButton = {
            BrutalistButton(text = "SAVE", onClick = { onSaveAvatar() })
        },
        dismissButton = {
            ComicButton(
                text = "CANCEL",
                onClick = { onCancelUpdateAvatar() },
                variant = ComicButtonVariant.Secondary
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp)
    )
}