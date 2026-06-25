package com.example.comicbookrental.ui.theme

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

object Dimens {

    val GridUnit = 4.dp

    object Spacing {
        val ScreenPadding = 16.dp
        val SectionSpacing = 24.dp
        val ContentSpacing = 12.dp
        val ListItemSpacing = 8.dp
        val AuthPadding = 24.dp
        val BorderSeparator = 3.dp
        val Margin = 20.dp
        val Gutter = 16.dp
        val StackSm = 8.dp
        val StackMd = 16.dp
        val StackLg = 32.dp
    }

    object Radius {
        val Sm = 4.dp
        val Default = 8.dp
        val Md = 12.dp
        val Lg = 16.dp
        val Xl = 24.dp
        val Full = 9999.dp

        val Card = 16.dp
        val Button = 12.dp
        val TextField = 12.dp
        val Inner = 4.dp
    }

    object Border {
        val Hairline = 1.5.dp
        val Standard = 2.dp
        val Focused = 3.dp
    }


    object Elevation {
        val None = 0.dp
        val Resting = 4.dp      // default popped-out look (4x4 ink offset)
        val RestingOffset  = DpOffset(4.dp, 4.dp)
        val Raised = 6.dp       // pressed / hovered lift
    }

    object Sizes {
        val ComicCoverWidth = 120.dp
        val ComicCoverHeight = 180.dp

        val SimilarCardWidth = 150.dp   // card width in the Detail "Similar Titles" row

        val ChipHeight = 28.dp      // compact genre tag (Comic Detail), smaller than a button
        val ButtonHeight = 48.dp
        val LargeButtonHeight = 56.dp

        val BottomBarHeight = 64.dp
        val AvatarSize = 48.dp
        
        val SecurityIndicatorHeight = 8.dp
        val OtpBoxWidth = 48.dp
        val OtpBoxHeight = 64.dp
        val SecurityAlertStripeWidth = 15.dp
        val AuthIconBoxSize = 72.dp
        val StripePatternWidth = 5.dp
    }

    object Icon {
        val Small = 16.dp
        val Medium = 24.dp
        val Large = 32.dp
    }

    object ShadowOffset{
        val shadowOffset = 6.dp
    }
}
