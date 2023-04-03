package com.kashif.common

import androidx.compose.ui.window.Application
import com.kashif.common.presentation.App
import com.kashif.common.presentation.components.PlatformVideoPlayer
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.addSubview
import platform.UIKit.didMoveToParentViewController

fun MainViewController(videoplayer:PlatformVideoPlayer): UIViewController {
  val viewController =   Application("Composables") {
        App(
            videoPlayerRenderer = {url->
                videoplayer.renderVideoPlayerView(url = url)
            })
    }
    val wrapperUIViewController = UIViewController()
    wrapperUIViewController.addChildViewController(viewController)
    wrapperUIViewController.view.addSubview(viewController.view)
    viewController.didMoveToParentViewController(wrapperUIViewController)
    return viewController
}
