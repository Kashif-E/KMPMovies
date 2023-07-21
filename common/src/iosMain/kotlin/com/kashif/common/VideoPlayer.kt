package com.kashif.common

import androidx.compose.ui.graphics.Color
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.play
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.UIKit.UIColor
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UILayoutConstraintAxisVertical
import platform.UIKit.UIStackView
import platform.UIKit.UIViewController
import platform.darwin.NSObject

class VideoPlayerViewController(
    private val url: String,
    private val backgroundColor: Color
) : UIViewController(nibName = null, bundle = null) {

    var onViewDisappeared: () -> Unit = {}

    val stack = UIStackView().apply {
        axis = UILayoutConstraintAxisVertical
        spacing = 16.0
        layoutMarginsRelativeArrangement = true
        layoutMargins = UIEdgeInsetsMake(24.0, 24.0, 24.0, 24.0)
        translatesAutoresizingMaskIntoConstraints = false
    }

    private inner class NSVideoPlayer : NSObject() {
        val playerLayer = AVPlayerLayer()

        fun renderVideoPlayerView(url: String) {
            val player = AVPlayer(uRL= NSURL.URLWithString(url)!!)
            playerLayer.player = player

            val rect = CGRectMake(100.0, 100.0, 200.0, 200.0)
            val frame = rect
            playerLayer.frame = frame

            player.play()
        }
    }

    override fun viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(
            red = backgroundColor.red.toDouble(),
            green = backgroundColor.green.toDouble(),
            blue = backgroundColor.blue.toDouble(),
            alpha = backgroundColor.alpha.toDouble(),
        )
        val nsVideoPlayer = NSVideoPlayer()
        view.layer.addSublayer(nsVideoPlayer.playerLayer)
        nsVideoPlayer.renderVideoPlayerView(url)
    }

    override fun viewDidDisappear(animated: Boolean) {
        super.viewDidDisappear(animated)
        onViewDisappeared()
    }
}

