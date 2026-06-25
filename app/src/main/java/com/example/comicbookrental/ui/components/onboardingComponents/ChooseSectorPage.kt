package com.example.comicbookrental.ui.components.onboardingComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary

private data class GenreItem(
    val genre: String,
    val imageUrl: String
)

@Composable
fun ChooseSectorPage(
    selectedGenres: Set<String>,
    onGenreClick: (String) -> Unit,
    onComplete: () -> Unit
)
{
    val mockGenres = listOf(
        "CYBERPUNK",
        "SCI-FI HORROR",
        "MYSTERY NOIR",
        "SHONEN",
        "POST-APOCALYPTIC",
        "FANTASY"
    )
    val imageList = listOf(
        "https://render.fineartamerica.com/images/images-profile-flow/400/images/artworkimages/mediumlarge/3/frequency-increase-sol-luckman.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMCNXg8Dp9cdVCSK-_xo1jXM2GBoFUB-y5Ag&s",
        "https://www.nme.com/wp-content/uploads/2024/05/NME-WISP-PLAYLIST-STORY-HERO-CREDIT-KRISTEN-JAN-WONG@2000x1270-696x442.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbQ1ufHEPMbT_E1izPJyWpig-o3NIlksU-UQ&s",
        "https://preview.redd.it/regarding-the-paintings-i-did-for-shoegaze-albums-have-i-v0-eutn3bstdz3h1.jpg?width=640&crop=smart&auto=webp&s=daefc493d1640faf36dfa3ba2daf6bdc2ef654a5",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiAefuObBkFH1FIGp2mtE5yqXKLBYbLw2Usw&s"
    )

    val genreItems = mockGenres.zip(imageList) { genre, url ->
        GenreItem(genre, url)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PANEL RUSH",
                fontFamily = Anton,
                fontSize = 20.sp,
                color = Primary
            )
        }

        HorizontalDivider(thickness = 3.dp, color = InkBlack)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            // Header Text
            Text(
                text = "CHOOSE YOUR SECTOR",
                fontFamily = Anton,
                fontSize = 32.sp,
                color = InkBlack,
                modifier = Modifier
                    .rotate(-3f)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select 3 or more genres to calibrate your personal multiverse feed.",
                style = MaterialTheme.typography.bodyMedium,
                color = Primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Genre Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(genreItems) { item ->
                    val isSelected = selectedGenres.contains(item.genre)
                    Box(
                        modifier = Modifier
                            .aspectRatio(0.8f)
                            .clickable { onGenreClick(item.genre) }
                            .comicHardShadow(
                                shape = RoundedCornerShape(0.dp),
                                offset = 4.dp,
                                color = InkBlack
                            )
                            .background(Color.White)
                            .border(
                                if (isSelected) 4.dp else 2.dp,
                                if (isSelected) Primary else InkBlack
                            )
                            .padding(8.dp)
                    ) {
                        // Image placeholder
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(InkBlack)
                                .border(2.dp, Color.Cyan)
                        )

                        // Text overlay
                        Text(
                            text = item.genre,
                            fontFamily = Anton,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                                .rotate(
                                    -5f
                                )
                        )

                        if (isSelected)
                        {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(24.dp)
                                    .background(
                                        Primary,
                                        CircleShape
                                    )
                                    .border(
                                        2.dp,
                                        InkBlack,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Action Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFEFEF))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${selectedGenres.size} SECTORS SELECTED",
                fontFamily = Anton,
                color = Primary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            val isEnabled = selectedGenres.size >= 3

            BrutalistButton(
                text = "ENTER THE MULTIVERSE",
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(),
//                enabled = isEnabled
            )
        }
    }
}
