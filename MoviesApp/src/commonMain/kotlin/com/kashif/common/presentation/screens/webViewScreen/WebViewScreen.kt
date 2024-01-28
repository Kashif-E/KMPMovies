package com.kashif.common.presentation.screens.webViewScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kashif.common.WebView
import com.kashif.common.presentation.components.TransparentIconHolder

class WebViewScreen(private val url: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
       Column(modifier = Modifier.fillMaxSize()) {
           Row(
               modifier =
               Modifier.height(70.dp)
                   .fillMaxWidth()
                   .background(color = Color.Black.copy(alpha = 0.6f)),
               horizontalArrangement = Arrangement.Start,
               verticalAlignment = Alignment.CenterVertically) {
               TransparentIconHolder(
                   icon = Icons.Rounded.ArrowBack,
               ) {
                   navigator.pop()
               }
           }
            WebView(modifier = Modifier.fillMaxSize(), url)

        }
    }
}
