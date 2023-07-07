package com.kashif.common

import androidx.compose.ui.window.ComposeUIViewController
import com.kashif.common.presentation.App
import platform.UIKit.UIView
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val viewController =
        ComposeUIViewController { App() }
            .also {
                //starts playing video but not visible
             /*   it.view.insertSubview(
                    videoplayer.returnVideoPlayerView(
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"),
                    atIndex = 0)*/
            }

    return viewController
}

interface PlatformVideoPlayer {

    fun renderVideoPlayerView(url: String)

    fun returnVideoPlayerView(url: String): UIView
}
