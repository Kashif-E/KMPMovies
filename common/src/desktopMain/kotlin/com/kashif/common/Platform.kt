package com.kashif.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
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

    Box {
        SwingPanel(
            factory = {  YoutubeVideoPlayer(videoId) },
            modifier =modifier,
            )
    }

}

@Composable
actual fun WebView(
    modifier: Modifier,
    link: String,

) {

    Box {
        SwingPanel(factory = {
            WebView(url = link)
        }, modifier = modifier)
    }

}