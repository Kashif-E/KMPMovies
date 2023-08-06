package com.kashif.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module { single { Android.create() } }

 actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO

@Composable
actual fun VideoPlayer(modifier: Modifier, videoId: String) {

    var playerRef: YouTubePlayerView? = null
    val youtubeListener =
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        }
    DisposableEffect(videoId) {
        playerRef?.addYouTubePlayerListener(youtubeListener)
        onDispose {
            playerRef?.release()
            playerRef?.removeYouTubePlayerListener(youtubeListener)
        }
    }

    AndroidView(
        factory = { context ->
            playerRef = YouTubePlayerView(context)
            playerRef!!.addYouTubePlayerListener(youtubeListener)
            playerRef!!
        })
}

class YouTubePlayerManager(private val context: Context) {
    private var youTubePlayer: YouTubePlayerView? = null

    fun initialize(listener: AbstractYouTubePlayerListener) {
        val view = YouTubePlayerView(context)
        view.addYouTubePlayerListener(listener)
        youTubePlayer = view
    }

    fun getPlayer(): YouTubePlayerView? = youTubePlayer

    fun release() {
        youTubePlayer?.release()
        youTubePlayer = null
    }
}

@SuppressLint("DiscouragedApi")
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font {
    val context = LocalContext.current
    val id = context.resources.getIdentifier(res, "font", context.packageName)
    return Font(id, weight, style)
}
