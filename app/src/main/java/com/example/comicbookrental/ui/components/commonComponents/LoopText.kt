package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun LoopText(
    value: String
){
    val ink = MaterialTheme.extendedColors.ink
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ink)
            .padding(vertical = Dimens.Spacing.ContentSpacing)
    ){
        Text(
            text = value,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.titleLarge.copy(fontFamily = Anton),
            modifier = Modifier.basicMarquee(
                iterations = Int.MAX_VALUE,
                velocity = 30.dp
            )
        )
    }
}