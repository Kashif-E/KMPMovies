package com.kashif.common

/*class VideoPlayerViewController(
    private val url: String,
    private val backgroundColor: Color,
    private val videoFrame: CValue<CGRect> // Use CGRect from platform.CoreGraphics
) : UIViewController(nibName = null, bundle = null) {

    var onViewDisappeared: () -> Unit = {}

    private inner class NSVideoPlayer : NSObject() {
        val playerLayer = AVPlayerLayer()

        fun renderVideoPlayerView(url: String) {
            val player = AVPlayer(uRL = NSURL.URLWithString(url)!!)
            playerLayer.player = player

            playerLayer.frame = videoFrame // Set the video player layer's frame

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
}*/





