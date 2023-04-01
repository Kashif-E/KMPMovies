package com.kashif.common

import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect fun platformModule(): Module

internal expect val ioDispatcher: CoroutineDispatcher

// commonMain/src/commonMain/kotlin/PlatformVideoPlayer.kt
