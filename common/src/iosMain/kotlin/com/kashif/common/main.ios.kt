package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Application
import com.kashif.common.presentation.App
import platform.UIKit.UIViewController


fun MainViewController(videoplayer: @Composable (url: String)-> Unit): UIViewController =
    Application("Composables") {
        App(androidAppVideoPlayer = videoplayer)
    }
