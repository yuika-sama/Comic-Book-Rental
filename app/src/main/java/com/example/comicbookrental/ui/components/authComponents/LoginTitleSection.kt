package com.example.comicbookrental.ui.components.authComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.AuthTitleSize
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun LoginTitleSection(){
    Text(
        text = "PANEL RUSH",
        style = MaterialTheme.typography.displayLarge.copy(fontSize = AuthTitleSize),
        color = MaterialTheme.colorScheme.primary
    )

    HorizontalDivider(
        color = MaterialTheme.extendedColors.ink, thickness = Dimens.Border.Standard
    )

    Spacer(modifier = Modifier.height(Dimens.Spacing.ListItemSpacing))

    Text(
        text = "YOUR NEXT ISSUE AWAITS",
        style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 2.sp),
        color = MaterialTheme.colorScheme.onSurface
    )
}