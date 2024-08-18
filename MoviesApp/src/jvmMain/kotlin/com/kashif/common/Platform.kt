package com.kashif.common

import DesktopWebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


import io.ktor.client.engine.java.*
import org.koin.dsl.module

actual fun platformModule() = module { single { Java.create() } }

@Composable
actual fun VideoPlayer(modifier: Modifier, videoId: String) {

DesktopWebView(
        modifier = modifier,
        url = "https://www.youtube.com/embed/$videoId"
    )

}

@Composable
actual fun WebView(
    modifier: Modifier,
    link: String,
) {
DesktopWebView(
        modifier = modifier,
        url = link
    )
}
