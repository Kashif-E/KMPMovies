package com.kashif.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.kashif.common.presentation.theme.DarkPH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import okio.FileSystem


@Composable
fun CachedAsyncImage(
    url: String, contentScale: ContentScale = ContentScale.Crop, modifier: Modifier
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalPlatformContext.current).data(url).crossfade(true)
            .build(),
        loading = {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = contentScale,
                modifier = modifier.placeHolder(DarkPH, true),
            )
        },
        contentScale = contentScale,
        contentDescription = null
    )


}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.DISABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(512L * 1024 * 1024) // 512MB
        .build()
}