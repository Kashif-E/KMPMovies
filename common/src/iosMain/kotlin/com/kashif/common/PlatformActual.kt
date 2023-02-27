package com.kashif.common

import io.ktor.client.engine.darwin.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


actual fun platformModule() = module {
    single {
        Darwin.create()
    }
}

internal actual val ioDispatcher: CoroutineDispatcher get() = Dispatchers.Default