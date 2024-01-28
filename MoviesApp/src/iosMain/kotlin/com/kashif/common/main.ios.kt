package com.kashif.common

import androidx.compose.ui.window.ComposeUIViewController
import com.kashif.common.presentation.ComposeApp
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    ComposeApp()
}
