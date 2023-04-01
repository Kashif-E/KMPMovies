package com.kashif.common.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.kashif.common.presentation.components.PlatformVideoPlayer
import com.kashif.common.presentation.theme.AppShapes
import com.kashif.common.presentation.theme.DarkColorPallete
import com.kashif.common.presentation.theme.Typography
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
internal fun App(androidAppVideoPlayer: PlatformVideoPlayer) {

    MaterialTheme(
        colors = DarkColorPallete,
        typography = Typography,
        shapes = AppShapes,
        content = { MoviesScreen(provide.screenModel, androidAppVideoPlayer) })
}

object provide : KoinComponent {
    val screenModel = get<HomeScreenViewModel>()
}
