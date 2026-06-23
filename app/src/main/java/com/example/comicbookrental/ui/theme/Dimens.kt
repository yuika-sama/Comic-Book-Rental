package com.example.comicbookrental.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Layout tokens for the "Heroic Kinetic" system (DESIGN.md).
 * Built on a 4dp base grid. Always pull dimensions from here — never hardcode dp in screens.
 */
object Dimens {

    /** Base grid unit (DESIGN.md spacing.unit = 4px). */
    val GridUnit = 4.dp

    object Spacing {
        // Semantic
        val ScreenPadding = 16.dp
        val SectionSpacing = 24.dp
        val ContentSpacing = 12.dp
        val ListItemSpacing = 8.dp
        val AuthPadding = 24.dp
        val BorderSeparator = 3.dp

        // DESIGN.md 8-point scale
        val Margin = 20.dp      // outer screen margin (4-col mobile grid)
        val Gutter = 16.dp      // visible space between cards
        val StackSm = 8.dp
        val StackMd = 16.dp
        val StackLg = 32.dp
    }

    object Radius {
        // DESIGN.md "rounded" scale
        val Sm = 4.dp
        val Default = 8.dp
        val Md = 12.dp
        val Lg = 16.dp
        val Xl = 24.dp
        val Full = 9999.dp

        // Semantic
        val Card = 16.dp
        val Button = 12.dp
        val TextField = 12.dp
        val Inner = 4.dp        // nested images inside cards
    }

    /** Ink-black stroke widths — every surface gets a hard border in this system. */
    object Border {
        val Hairline = 1.5.dp   // subtle dividers
        val Standard = 2.dp     // default card / button / input border
        val Focused = 3.dp      // input field when focused
    }

    /**
     * Hard-offset shadow distances (NO Gaussian blur). Elements "pop" off the page;
     * interactive ones lift higher by increasing the offset. Consumed by the
     * brutalist shadow modifier (added when building components).
     */
    object Elevation {
        val None = 0.dp
        val Resting = 4.dp      // default popped-out look (4x4 ink offset)
        val Raised = 6.dp       // pressed / hovered lift
    }

    object Sizes {
        val ComicCoverWidth = 120.dp
        val ComicCoverHeight = 180.dp

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
