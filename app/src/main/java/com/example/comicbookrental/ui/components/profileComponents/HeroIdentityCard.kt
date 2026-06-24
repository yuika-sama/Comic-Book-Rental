package com.example.comicbookrental.ui.components.profileComponents

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary
import com.example.comicbookrental.ui.theme.PrimaryContainer
import com.example.comicbookrental.ui.theme.Success
import java.io.File

@Preview
@Composable
fun HeroIdentityCardPreview(){
    HeroIdentityCard(
        imageUrl = "https://i.pinimg.com/originals/90/21/9c/90219cb3652183e0513452cc2586655f.jpg",
        heroName = "YUIKA",
        rank = "VIP",
        email = "william.henry.harrison@example-pet-store.com",
        isVerified = true,
    )
}

@Composable
fun HeroIdentityCard(
    imageUrl: String? = null,
    heroName: String,
    rank: String,
    email: String,
    isVerified: Boolean? = false,
    onAvatarClick: () -> Unit = {}
) {
    NeoBox(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.clickable{
                    onAvatarClick()
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .offset(x = 4.dp, y = 4.dp)
                        .background(Color.Blue, CircleShape)
                )
                if (!imageUrl.isNullOrEmpty()) {
                    val context = LocalContext.current
                    val imageModel = if (imageUrl.startsWith("file://")) {
                        File(imageUrl.removePrefix("file://"))
                    } else {
                        imageUrl
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageModel)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                            .border(3.dp, InkBlack, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                            .border(3.dp, InkBlack, CircleShape)
                    )
                }
                // Angled Badge
                Box(
                    modifier = Modifier
                        .offset(y = 12.dp, x = 20.dp)
                        .rotate(-8f)
                        .background(PrimaryContainer)
                        .border(2.dp, InkBlack)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .zIndex(2f)
                ) {
                    Text(rank, color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name
            Text(
                text = heroName,
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xFF6B4C9A),
                        offset = Offset(6f, 6f)
                    )
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Identity Box
            Column () {
                if (isVerified == true){
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .background(InkBlack)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Success, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("VERIFIED", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
            Text(
                text = email,
                color = InkBlack,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}