package com.kashif.common.domain.di

import com.kashif.common.data.remote.AbstractKtorService
import com.kashif.common.data.remote.KtorService
import com.kashif.common.data.repository.AbstractRepository
import com.kashif.common.data.repository.Repository
import com.kashif.common.domain.usecase.*
import com.kashif.common.platformModule
import com.kashif.common.presentation.screens.detailsScreen.DetailsScreenViewModel
import com.kashif.common.presentation.screens.home.HomeScreenViewModel
import com.kashif.common.presentation.screens.trailerScreen.TrailerScreenViewModel
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    enableNetworkLogs: Boolean = false,
    baseUrl: String,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(commonModule(enableNetworkLogs = enableNetworkLogs, baseUrl))
}

// called by iOS etc
fun initKoin(baseUrl: String) = initKoin(enableNetworkLogs = true, baseUrl = baseUrl) {}

fun commonModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) =
    platformModule() +
        getDataModule(enableNetworkLogs, baseUrl) +
        getUseCaseModule() +
        getScreenModelModule()

fun getScreenModelModule() = module {
    single { HomeScreenViewModel(get(), get(), get(), get(), get(), get()) }
    single { DetailsScreenViewModel(get()) }
    single {  TrailerScreenViewModel(get())}
}

fun getDataModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) = module {
    single<AbstractRepository> { Repository(get()) }

    single<AbstractKtorService> { KtorService(get(), baseUrl = baseUrl) }

    single { createJson() }

    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }
}

fun getUseCaseModule() = module {
    single { GetPopularMoviesUseCase(get()) }
    single { GetLatestMoviesPagingSource(get()) }
    single { GetPopularMoviesPagingSource(get()) }
    single { GetNowPlayingMoviesPagingSource(get()) }
    single { GetTopRatedMoviesPagingSource(get()) }
    single { GetUpcomingMoviesPagingSource(get()) }
    single { GetMovieTrailerUseCase(get()) }
    single { GetMovieDetailsUseCase(get()) }

}

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        defaultRequest { header("api_key", "9da3e04402ffad9e5a100c5569dc26b1") }

        // exception handling
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> !response.status.isSuccess() }
            retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException }
            delayMillis { 3000L } // retries in 3, 6, 9, etc. seconds
        }

        install(HttpCallValidator) {
            handleResponseExceptionWithRequest { cause, _ -> println("exception $cause") }
        }

        install(ContentNegotiation) { json(json) }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

fun createJson() = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}
