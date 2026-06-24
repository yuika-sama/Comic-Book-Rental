package com.example.comicbookrental.ui.components.authComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun SecurityAlert(
    message: String
){
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink
    val primaryColor = MaterialTheme.colorScheme.primary

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .border(Dimens.Border.Standard, ink, shape)
            .background(MaterialTheme.colorScheme.background)
            .padding(end = Dimens.Spacing.Gutter),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .width(Dimens.Sizes.SecurityAlertStripeWidth)
                .fillMaxHeight()
                .drawBehind{
                    val stripeWidth = Dimens.Sizes.StripePatternWidth.toPx()
                    var x = 0f
                    while (x < size.width + size.height){
                        drawLine(
                            color = primaryColor,
                            start = Offset(x, 0f),
                            end = Offset(x - size.height, size.height),
                            strokeWidth = stripeWidth
                        )
                        x += stripeWidth * 2
                    }
                }
                .border(width = Dimens.Border.Standard, color = ink)
        )

        Spacer(modifier = Modifier.width(Dimens.Spacing.Gutter))

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Dimens.Icon.Large)
        )

        Spacer(modifier = Modifier.width(Dimens.Spacing.ContentSpacing))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append("SECURITY ALERT\n")
                }
                append(message)
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = Dimens.Spacing.ContentSpacing)
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
fun SecurityAlertPreview(){
    SecurityAlert(
        message = " Super hot manga"
    )
}