package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.kashif.common.presentation.components.DesktopWebView
import io.ktor.client.engine.java.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module { single { Java.create() } }

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
