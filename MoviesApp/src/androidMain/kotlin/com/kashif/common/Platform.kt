package com.kashif.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.kashif.common.presentation.components.CircularProgressbarAnimated
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module { single { Android.create() } }

@Composable
actual fun WebView(modifier: Modifier, link: String) {
    var isLoading by remember { mutableStateOf(true) }
    Box {
        CircularProgressbarAnimated(modifier = Modifier.wrapContentHeight().align(Alignment.Center))
        AndroidView(
            factory = {
                WebView(it).apply {
                    //settings.javaScriptEnabled = true
                    layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                    webViewClient =
                        object : WebViewClient() {

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                isLoading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }
                        }
                    loadUrl(link)
                }
            },
            update = {})
    }
}
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    videoId: String,
) {
    var playerRef: YouTubePlayerView? = null
    val youtubeListener = remember {
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)

            }
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
        modifier = modifier,
        factory = { context ->
            playerRef = YouTubePlayerView(context).apply {
                addYouTubePlayerListener(youtubeListener)
            }
            playerRef!!
        },
        update = { view ->
            view.getYouTubePlayerWhenReady(object : YouTubePlayerCallback{
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {

                    youTubePlayer.unMute()
                    youTubePlayer.setVolume(100)
                }

            })

        }
    )
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

