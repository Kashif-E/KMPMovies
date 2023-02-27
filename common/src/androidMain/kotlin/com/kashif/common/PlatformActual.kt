package com.kashif.common

import android.content.Context
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        Android.create()
    }
}
internal actual val ioDispatcher: CoroutineDispatcher get() = Dispatchers.IO

