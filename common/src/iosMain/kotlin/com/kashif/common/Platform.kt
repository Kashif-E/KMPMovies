package com.kashif.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.core.lifecycle.DefaultScreenLifecycleOwner.onDispose
import io.ktor.client.engine.darwin.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module { single { Darwin.create() } }

internal actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.Default

@Composable
actual fun VideoPlayer(modifier: Modifier, url: String) {
    val viewController = LocalUIViewController.current
    val backgroundColor = MaterialTheme.colors.background
    val datePickerViewController = remember {
        VideoPlayerViewController(url, backgroundColor = backgroundColor)
    }

    // Use LaunchedEffect to present the view controller when the view is committed
    LaunchedEffect(viewController, datePickerViewController) {
        viewController.presentViewController(datePickerViewController, true, null)

        // Dismiss the view controller when the composable is disposed

    }
}



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

