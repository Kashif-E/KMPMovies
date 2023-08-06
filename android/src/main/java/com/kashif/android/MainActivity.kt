package com.kashif.android

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.kashif.common.main.Application
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.option.androidContext
import com.kashif.common.domain.util.commonConfig
import okio.Path.Companion.toOkioPath

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalImageLoader provides remember { generateImageLoader(applicationContext) },
            ) {
                Application()
            }
        }
    }
}

private fun generateImageLoader(applicationContext: Context): ImageLoader {
    return ImageLoader {
        commonConfig()
        options { androidContext(applicationContext) }
        components { setupDefaultComponents() }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(applicationContext, 0.25)
            }
            diskCacheConfig {
                directory(applicationContext.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}
