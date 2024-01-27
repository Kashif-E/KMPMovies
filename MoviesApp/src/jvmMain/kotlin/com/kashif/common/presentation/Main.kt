package com.kashif.common.presentation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.kashif.common.domain.di.initKoin

fun main() = application {
    Window(
        title = "Movie App",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = {
            exitApplication()
        },
    ) {
        initKoin("https://api.themoviedb.org/3/")
        ComposeApp()
    }
}