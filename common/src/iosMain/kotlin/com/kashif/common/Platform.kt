package com.kashif.common

import androidx.compose.ui.graphics.ImageBitmap
import com.kashif.common.presentation.components.AsyncImage
import io.ktor.client.engine.darwin.*
import org.koin.dsl.module


actual fun platformModule() = module {
    single {
        Darwin.create()
    }
}
