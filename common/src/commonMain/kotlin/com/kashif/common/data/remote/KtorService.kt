package com.kashif.common.data.remote

import com.kashif.common.data.dto.MoviesDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class KtorService(private val httpClient: HttpClient, private val baseUrl: String) :
    AbstractKtorService() {
    private val apiKey = "9da3e04402ffad9e5a100c5569dc26b1"
    override suspend fun getPopularMovies(pageNo: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.POPULAR_MOVIES.getUrl(apiKey, pageNo)).body()
}
