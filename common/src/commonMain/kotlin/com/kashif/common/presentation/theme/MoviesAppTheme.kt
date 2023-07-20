package com.kashif.common.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green

val colorPallete =
    darkColors(
        primary = GreenSecondary,
        primaryVariant = YellowGreen,
        secondary = Green,
        secondaryVariant = YellowGreenSecondary,
        background = Black,
        surface = Black,
        onPrimary = Color.White,
        onSecondary = DarkGrey,
        onBackground = DarkGrey,
        onSurface = DarkGrey,
        error = Danger,
        onError = Color.Red
    )

@Composable
fun MoviesAppTheme( content : @Composable () -> Unit){
    MaterialTheme(
        colors = colorPallete,
        typography = getTypography(),
        shapes = AppShapes,
        content = {
           content()
        })
}
