package com.kashif.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.kashif.common.presentation.App
import org.koin.core.context.GlobalContext.get


@Preview
@Composable
fun Application() {
    App()
}