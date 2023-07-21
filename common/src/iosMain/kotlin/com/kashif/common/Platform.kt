package com.kashif.common

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.CValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRect
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

actual fun platformModule() = module { single { Darwin.create() } }

internal actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.Default

//only layer
/*
@Composable
actual fun VideoPlayer(modifier: Modifier, url: String) {
    val player = remember { AVPlayer(uRL = NSURL.URLWithString(url)!!) }
    val playerLayer = remember { AVPlayerLayer() }

    playerLayer.player = player
    // Use a UIKitView to integrate with your existing UIKit views
    UIKitView(
        factory = {
            // Create a UIView to hold the AVPlayerLayer
            val playerContainer = UIView()
            playerContainer.layer.addSublayer(playerLayer)
            // Return the playerContainer as the root UIView
            playerContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            playerLayer.setFrame(rect)
            CATransaction.commit()
        },
        update = { view -> player.play() },
        modifier = modifier)
}
*/

@Composable
actual fun VideoPlayer(modifier: Modifier, url: String) {
    val player = remember { AVPlayer(uRL = NSURL.URLWithString(url)!!) }
    val playerLayer = remember { AVPlayerLayer() }
    val avPlayerViewController = remember { AVPlayerViewController() }
    avPlayerViewController.player = player
    avPlayerViewController.showsPlaybackControls = true

    playerLayer.player = player
    // Use a UIKitView to integrate with your existing UIKit views
    UIKitView(
        factory = {
            // Create a UIView to hold the AVPlayerLayer
            val playerContainer = UIView()
            playerContainer.addSubview(avPlayerViewController.view)
            // Return the playerContainer as the root UIView
            playerContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            playerLayer.setFrame(rect)
            avPlayerViewController.view.layer.frame = rect
            CATransaction.commit()
        },
        update = { view ->
            player.play()
            avPlayerViewController.player!!.play()
        },
        modifier = modifier)
}

// Working on z index
/*@Composable
actual fun VideoPlayer(modifier: Modifier, url: String) {
    val viewController = LocalUIViewController.current

    // Create an AVPlayer and AVPlayerLayer
    val player = remember { AVPlayer(uRL = NSURL.URLWithString(url)!!) }
    val playerLayer = remember { AVPlayerLayer() }
    playerLayer.player = player

    // Use the layout modifier to get the size of the video player view
    Box(modifier = modifier.border(BorderStroke(1.dp, Color.Red))) {
        // Apply the videoFrame size to the AVPlayerLayer
        val videoFrame = CGRectMake(
            x = 0.0,
            y = 0.0,
            width = width.toDouble(),
            height = height.toDouble()
        )
        playerLayer.frame = videoFrame

        // Add the AVPlayerLayer to the current UIViewController's view
        DisposableEffect(playerLayer) {

            viewController.view.layer.addSublayer(playerLayer)

            // Remove the AVPlayerLayer from the UIViewController's view on dispose
            onDispose {
                playerLayer.removeFromSuperlayer()
            }
        }

        // Play the video
        player.play()

        // Play/Pause control
        var isPlaying by remember { mutableStateOf(true) }
        PlayPauseButton(
            isPlaying = isPlaying,
            onClick = {
                isPlaying = !isPlaying
                if (isPlaying) {
                    player.play()
                } else {
                    player.pause()
                }
            }
        )

        // Loading indicator
        val isLoading by remember { mutableStateOf(true) }
        LoadingIndicator(isLoading = isLoading)
    }
}*/

@Composable
fun PlayPauseButton(isPlaying: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White)
    }
}

@Composable
fun LoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(color = Color.White)
    }
}

/*@Composable
actual fun VideoPlayer(modifier: Modifier, url: String, width : Int, height : Float) {
    val viewController = LocalUIViewController.current
    val backgroundColor = MaterialTheme.colors.background


    val player = remember { AVPlayer(uRL = NSURL.URLWithString(url)!!) }
    val playerLayer = remember { AVPlayerLayer() }
    playerLayer.player = player
    // Use the layout modifier to get the size of the video player view
    Column(modifier = modifier) {
        val videoFrame = CGRectMake(x=0.00,y= 0.00, width = width.toDouble(), height = height.toDouble())
        playerLayer.frame = videoFrame
        Text("ssss", color = Color.Green)
        // Create the VideoPlayerViewController with the videoFrame size
        val datePickerViewController = remember {
            VideoPlayerViewController(url, backgroundColor = backgroundColor, videoFrame = videoFrame)
        }

        // Use LaunchedEffect to present the view controller when the view is committed
        DisposableEffect(viewController, datePickerViewController) {
            viewController.addChildViewController(datePickerViewController)
            datePickerViewController.didMoveToParentViewController(viewController)
            viewController.view.addSubview(datePickerViewController.view)

            onDispose {
                datePickerViewController.view.removeFromSuperview()
                datePickerViewController.removeFromParentViewController()
            }
        }
    }
}*/

/*actual fun VideoPlayer(modifier: Modifier, url: String) {
    val viewController = LocalUIViewController.current
    val backgroundColor = MaterialTheme.colors.background

    // Create a mutable state to store the size of the video player view
    val sizeState = remember { mutableStateOf(Size(300F, 300F)) }

    // Use the layout modifier to get the size of the video player view
    Column(modifier = modifier) {
        val videoFrame = CGRectMake(
            x = 0.0,
            y = 0.0,
            width = sizeState.value.width.toDouble(),
            height = sizeState.value.height.toDouble()
        )
        Text("hibby , dobby ", color = Color.Green)

        // Create the VideoPlayerViewController with the videoFrame size
        val datePickerViewController = remember {
            VideoPlayerViewController(url, backgroundColor = backgroundColor, videoFrame = videoFrame)
        }

        // Use LaunchedEffect to present the view controller when the view is committed
        DisposableEffect(viewController, datePickerViewController) {
            viewController.addChildViewController(datePickerViewController)
            datePickerViewController.didMoveToParentViewController(viewController)
            viewController.view.addSubview(datePickerViewController.view)

            onDispose {
                datePickerViewController.view.removeFromSuperview()
                datePickerViewController.removeFromParentViewController()
            }
        }
    }
}*/

/*
fun MainViewController(): UIViewController {
    val viewController = ComposeUIViewController {
        App(
            videoPlayerRenderer = { url ->
                // starts playing video and visible but in front of other views
                // videoplayer.renderVideoPlayerView(url)
            })
    }

    viewController.viewWillLayoutSubviews()

    // Create an instance of VideoPlayer
    val videoPlayer = VideoPlayer()
    // Render the video player view with the desired URL
    videoPlayer.renderVideoPlayerView(
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")

    // Add playerLayer to the view hierarchy
    viewController.view.layer.addSublayer(videoPlayer.playerLayer)

    return viewController
}
*/
