package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.ComposeUIViewController
import com.kashif.common.presentation.App
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.UIKit.UIView
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val viewController =
        ComposeUIViewController { App() }
            .also {
                //starts playing video but not visible
             /*   it.view.insertSubview(
                    videoplayer.returnVideoPlayerView(
                    ),
                    atIndex = 0)*/
            }

    return viewController
}




private val cache: MutableMap<String, Font> = mutableMapOf()
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font {
    return cache.getOrPut(res) {
        val byteArray = runBlocking {
            resource("font/$res.ttf").readBytes()
        }
        androidx.compose.ui.text.platform.Font(res, byteArray, weight, style)
    }
}

