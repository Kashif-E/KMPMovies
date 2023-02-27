package com.kashif.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.ImageRequestState
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
internal fun AsyncImage(url: String, modifier: Modifier) {


    val painter = rememberAsyncImagePainter(url = url)
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
