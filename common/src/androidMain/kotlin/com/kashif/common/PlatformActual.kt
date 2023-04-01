package com.kashif.common

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kashif.common.presentation.components.PlatformVideoPlayer
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module { single { Android.create() } }

internal actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO


class AndroidAppVideoPlayer : PlatformVideoPlayer {
    @Composable
    override fun renderVideoPlayerView(url: String) {
        VideoPlayer(url)
    }
}

@Composable
fun VideoPlayer(url: String) {

    AndroidView(
        modifier = Modifier.height(200.dp).fillMaxWidth(),
        factory = { context ->
            VideoView(context).apply {
                setVideoPath(url)
                val mediaController = MediaController(context)
                mediaController.setAnchorView(this)
                setMediaController(mediaController)
                start()
            }
        },
        update = {})
}
