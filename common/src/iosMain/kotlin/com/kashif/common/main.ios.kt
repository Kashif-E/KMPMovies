package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.ComposeUIViewController
import com.kashif.common.domain.util.commonConfig
import com.kashif.common.presentation.App
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toPath
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    CompositionLocalProvider(
        LocalImageLoader provides remember { generateImageLoader() },
    ) {
        App()
    }
}

/**
 * Cache for image loader
 */
private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        commonConfig()
        components { setupDefaultComponents() }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(0.25)
            }
            diskCacheConfig {
                directory(getCacheDir().toPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun getCacheDir(): String {
    return NSFileManager.defaultManager
        .URLForDirectory(
            NSCachesDirectory,
            NSUserDomainMask,
            null,
            true,
            null,
        )!!
        .path
        .orEmpty()
}


/**
 *
 * Apply custom fonts
 */
private val cache: MutableMap<String, Font> = mutableMapOf()

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font {
    return cache.getOrPut(res) {
        val byteArray = runBlocking { resource("font/$res.ttf").readBytes() }
        androidx.compose.ui.text.platform.Font(res, byteArray, weight, style)
    }
}
