package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun OtpInputRow(otpCount: Int){
    val otpValues = remember { mutableStateListOf(*Array(otpCount){""}) }
    val focusRequesters = remember { List(otpCount) { FocusRequester() } }
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        for (i in 0 until otpCount){
            BasicTextField(
                value = otpValues[i],
                onValueChange = {
                    if (it.length <= 1){
                        otpValues[i] = it
                        if (it.isNotEmpty() && i < otpCount - 1){
                            focusRequesters[i+1].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .width(Dimens.Sizes.OtpBoxWidth)
                    .height(Dimens.Sizes.OtpBoxHeight)
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .border(Dimens.Border.Standard, ink, shape)
                    .focusRequester(focusRequesters[i]),
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        innerTextField()
                    }
                }
            )
        }
    }
}