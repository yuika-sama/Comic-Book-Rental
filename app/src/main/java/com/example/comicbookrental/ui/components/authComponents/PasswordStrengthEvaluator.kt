package com.example.comicbookrental.ui.components.authComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.Error
import com.example.comicbookrental.ui.theme.Success
import com.example.comicbookrental.ui.theme.TextSecondary
import com.example.comicbookrental.ui.theme.Warning
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun PasswordStrengthEvaluator(
    strength: Int,
    label: String
) {
    val ink = MaterialTheme.extendedColors.ink
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Radius.Sm)
        ) {
            repeat(4) { index ->
                val color = when {
                    index < strength && strength <= 1 -> Error
                    index < strength && strength <= 2 -> Warning
                    index < strength && strength <= 4 -> Success
                    else -> TextSecondary
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimens.Sizes.SecurityIndicatorHeight)
                        .border(Dimens.Border.Hairline, ink)
                        .background(color)
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.Spacing.StackSm))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}