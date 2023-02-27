package com.kashif.common

import androidx.compose.ui.window.Application
import com.kashif.common.presentation.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController =
    Application("Example Application") { App(ServiceProvider.screenModel) }
