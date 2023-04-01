package com.kashif.common

import androidx.compose.ui.window.Application
import com.kashif.common.presentation.App
import com.kashif.common.presentation.components.PlatformVideoPlayer
import platform.UIKit.UIViewController

fun MainViewController(videoPlayer: PlatformVideoPlayer): UIViewController =
    Application("Composables") { App(videoPlayer) }


