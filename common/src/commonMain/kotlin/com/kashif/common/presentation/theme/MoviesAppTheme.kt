package com.kashif.common.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green

val LightColorPalette =
    lightColors(
        primary = GreenSecondary,
        primaryVariant = DarkBlue,
        secondary = Green,
        secondaryVariant = YellowGreenSecondary,
        background = Background,
        surface = Background,
        onPrimary = Color.White,
        onSecondary = OnPrimary,
        onBackground = DarkBlue,
        onSurface = DarkBlue,
        error = Danger,
        onError = OnPrimary
    )

 val DarkColorPallete =
    darkColors(
        primary = GreenSecondary,
        primaryVariant = YellowGreen,
        secondary = Green,
        secondaryVariant = YellowGreenSecondary,
        background = DarkBlue,
        surface = DarkBlue,
        onPrimary = Color.White,
        onSecondary = DarkGrey,
        onBackground = DarkGrey,
        onSurface = DarkGrey,
        error = Danger,
        onError = Color.Red
    )

