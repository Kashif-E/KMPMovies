package com.kashif.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import org.koin.core.context.GlobalContext.get


@Preview
@Composable
fun Application() {
    App(get().get())
}