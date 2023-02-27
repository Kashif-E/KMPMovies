package com.kashif.common.main

import androidx.compose.runtime.Composable
import com.kashif.common.presentation.App
import org.koin.androidx.compose.get

@Composable
fun Application() {
    App(get())
}
