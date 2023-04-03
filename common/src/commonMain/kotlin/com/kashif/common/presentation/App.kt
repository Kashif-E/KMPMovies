package com.kashif.common.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.kashif.common.presentation.theme.AppShapes
import com.kashif.common.presentation.theme.DarkColorPallete
import com.kashif.common.presentation.theme.Typography
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
internal fun App(videoPlayerRenderer : @Composable (String) -> Unit) {

    MaterialTheme(
        colors = DarkColorPallete,
        typography = Typography,
        shapes = AppShapes,
        content = { MoviesScreen(provide.screenModel,videoPlayerRenderer) })
}
object provide : KoinComponent {
    val screenModel = get<HomeScreenViewModel>()
}
