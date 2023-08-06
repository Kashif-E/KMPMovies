package com.kashif

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kashif.common.Application
import com.kashif.common.domain.di.initKoin

fun main() = application {
    initKoin("https://api.themoviedb.org/3/")

    Window(
        title = "Compose Movies",
        onCloseRequest = { exitApplication() },
    ) {
       Application()
    }
}
