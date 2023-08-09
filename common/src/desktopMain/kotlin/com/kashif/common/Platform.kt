package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.ktor.client.engine.java.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module { single { Java.create() } }

actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO

@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font =
    androidx.compose.ui.text.platform.Font("font/$res.ttf", weight, style)

@Composable
actual fun VideoPlayer(modifier: Modifier, videoId: String) {

    DesktopWebView(modifier, "https://www.youtube.com/embed/$videoId")
}

@Composable
actual fun WebView(
    modifier: Modifier,
    link: String,
) {

    DesktopWebView(modifier, link)
}
