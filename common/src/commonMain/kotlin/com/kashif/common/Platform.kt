package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect fun platformModule(): Module

internal expect val ioDispatcher: CoroutineDispatcher

@Composable
expect fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font

