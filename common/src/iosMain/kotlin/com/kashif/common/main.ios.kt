package com.kashif.common

import androidx.compose.ui.window.Application
import com.kashif.common.presentation.App
import platform.UIKit.*

fun MainViewController(videoplayer: PlatformVideoPlayer): UIViewController {
    val viewController =
        Application("Composables") { App(videoPlayerRenderer = { url ->
            //starts playing video and visible but in front of other views
            videoplayer.renderVideoPlayerView(url)
        }) }
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
