package com.kashif.common.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.kashif.common.presentation.Application
import com.kashif.common.presentation.components.SlideTransition
import com.kashif.common.presentation.tabs.HomeTab

val LightColorPalette =
    lightColors(
        primary = GreenSecondary,
        primaryVariant = DarkBlue,
        secondary = Green,
        secondaryVariant = YellowGreenSecondary,
        background = Color.Black,
        surface = Color.Black,
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
        background = Color.Black,
        surface = Color.Black,
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
        colors = DarkColorPallete,
        typography = Typography,
        shapes = AppShapes,
        content = {
           content()
        })
}
