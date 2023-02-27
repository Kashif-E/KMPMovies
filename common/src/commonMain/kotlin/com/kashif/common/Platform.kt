package com.kashif.common

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect fun platformModule(): Module

internal expect val ioDispatcher: CoroutineDispatcher

