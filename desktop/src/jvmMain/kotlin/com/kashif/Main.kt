package com.kashif

import androidx.compose.ui.window.singleWindowApplication
import com.kashif.common.Application
import com.kashif.common.domain.di.initKoin

fun main() = singleWindowApplication(title = "Compose Movies") {
    initKoin("https://api.themoviedb.org/3/")

    Application()
}
