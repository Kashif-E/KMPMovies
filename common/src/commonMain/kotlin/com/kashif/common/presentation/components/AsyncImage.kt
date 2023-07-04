package com.kashif.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
internal fun AsyncImage(
    url: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier
) {

    val painter = rememberAsyncImagePainter(url = url)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier,
    )
    when (val requestState = painter.requestState) {
        is ImageRequestState.Failure -> {
            Text(requestState.error.message ?: "Error")
        }
        ImageRequestState.Success -> Unit
        is ImageRequestState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
