package com.example.comicbookrental.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.*

@Composable
fun DesignTestScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(Dimens.Spacing.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.SectionSpacing
        )
    ) {

        item {
            SectionTitle("Colors")
            ColorPalette()
        }

        item {
            SectionTitle("Typography")
            TypographySection()
        }

        item {
            SectionTitle("Buttons")
            ButtonSection()
        }

        item {
            SectionTitle("Dimensions")
            DimensionSection()
        }
    }
}

@Composable
private fun SectionTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
private fun ColorPalette() {

    Column(
        verticalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ListItemSpacing
        )
    ) {

        Text(
            "Brand",
            style = MaterialTheme.typography.titleMedium
        )

        ColorItem("Primary", Primary)
        ColorItem("Primary Container", PrimaryContainer)

        ColorItem("Secondary", Secondary)
        ColorItem("Secondary Container", SecondaryContainer)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Status",
            style = MaterialTheme.typography.titleMedium
        )

        ColorItem("Success", Success)
        ColorItem("Warning", Warning)
        ColorItem("Error", Error)
        ColorItem("Info", Info)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Content",
            style = MaterialTheme.typography.titleMedium
        )

        ColorItem("Rating", Rating)

        ColorItem("Background", Background)
        ColorItem("Surface", Surface)
        ColorItem("Surface Variant", SurfaceVariant)

        ColorItem("Text Primary", OnSurface)
        ColorItem("Text Secondary", OnSurfaceVariant)
        ColorItem("Text Disabled", Outline)

        ColorItem("Border", OutlineVariant)
    }
}

@Composable
private fun ColorItem(
    label: String,
    color: Color
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ContentSpacing
        )
    ) {

        Surface(
            modifier = Modifier.size(48.dp),
            color = color,
            shape = RoundedCornerShape(Dimens.Radius.Card)
        ) {}

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun TypographySection() {

    Column(
        verticalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ListItemSpacing
        )
    ) {

        Text(
            "Headline Large",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            "Headline Medium",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            "Title Large",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            "Body Large",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            "Body Medium",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            "Body Small",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ButtonSection() {

    Column(
        verticalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ContentSpacing
        )
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Sizes.ButtonHeight),
            shape = RoundedCornerShape(Dimens.Radius.Button),
            onClick = {}
        ) {
            Text("Primary Button")
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Sizes.ButtonHeight),
            shape = RoundedCornerShape(Dimens.Radius.Button),
            onClick = {}
        ) {
            Text("Outlined Button")
        }

        TextButton(
            onClick = {}
        ) {
            Text("Text Button")
        }
    }
}

@Composable
private fun DimensionSection() {

    Column(
        verticalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ContentSpacing
        )
    ) {

        Text(
            "Screen Padding = ${Dimens.Spacing.ScreenPadding}"
        )

        Text(
            "Section Spacing = ${Dimens.Spacing.SectionSpacing}"
        )

        Text(
            "Content Spacing = ${Dimens.Spacing.ContentSpacing}"
        )

        Text(
            "Card Radius = ${Dimens.Radius.Card}"
        )

        Text(
            "Button Height = ${Dimens.Sizes.ButtonHeight}"
        )

        Text(
            "Comic Cover = ${Dimens.Sizes.ComicCoverWidth} x ${Dimens.Sizes.ComicCoverHeight}"
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                Dimens.Radius.Card
            )
        ) {

            Row(
                modifier = Modifier.padding(
                    Dimens.Spacing.ScreenPadding
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    Dimens.Spacing.ContentSpacing
                )
            ) {

                // Cover
                Box(
                    modifier = Modifier
                        .size(
                            width = Dimens.Sizes.ComicCoverWidth,
                            height = Dimens.Sizes.ComicCoverHeight
                        )
                        .background(
                            PrimaryContainer,
                            RoundedCornerShape(
                                Dimens.Radius.Card
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = OutlineVariant,
                            shape = RoundedCornerShape(
                                Dimens.Radius.Card
                            )
                        )
                )

                // Content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(
                        Dimens.Spacing.ListItemSpacing
                    )
                ) {

                    Text(
                        text = "Re: Zero - Starting Life in Another World",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Author: Tappei Nagatsuki",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )

                    Text(
                        text = "⭐ 4.8",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "The story centers on Subaru Natsuki, a hikikomori who suddenly finds himself transported to another world while on his way home from a convenience store.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant,
                        maxLines = 3
                    )

                    Surface(
                        color = Success.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = "Available",
                            color = Success,
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 4.dp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DesignTestPreview() {
        DesignTestScreen()
}