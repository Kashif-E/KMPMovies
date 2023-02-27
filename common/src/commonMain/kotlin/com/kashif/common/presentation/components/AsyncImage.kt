package com.kashif.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.intercept.Interceptor
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.model.NullRequestData
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
internal fun AsyncImage(url: String, modifier: Modifier) {

    val request =
        remember(url) {
            ImageRequest {
                data(url)
                addInterceptor(NullDataInterceptor)
            }
        }
    val painter = rememberAsyncImagePainter(request)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
    when (val requestState = painter.requestState) {
        ImageRequestState.Loading -> {
            CircularProgressIndicator()
        }
        is ImageRequestState.Failure -> {
            Text(requestState.error.message ?: "Error")
        }
        ImageRequestState.Success -> Unit
    }
}

/** return empty painter if request is null or empty */
object NullDataInterceptor : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        if (data === NullRequestData || data is String && data.isEmpty()) {
            return ImageResult.Painter(
                request = chain.request,
                painter = EmptyPainter,
            )
        }
        return chain.proceed(chain.request)
    }

    private object EmptyPainter : Painter() {
        override val intrinsicSize: Size
            get() = Size.Unspecified
        override fun DrawScope.onDraw() {}
    }
}
