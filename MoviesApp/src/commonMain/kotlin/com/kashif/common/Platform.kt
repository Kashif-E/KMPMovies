package com.kashif.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module

expect fun platformModule(): Module

@Composable
expect fun VideoPlayer(modifier: Modifier, videoId: String)

@Composable
expect fun WebView(
 modifier: Modifier,
 link: String,
 )