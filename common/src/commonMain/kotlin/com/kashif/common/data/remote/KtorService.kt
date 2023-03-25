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

    override suspend fun getTopRatedMovies(pageNo: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.TOP_RATED_MOVIES.getUrl(apiKey, pageNo)).body()

    override suspend fun getUpcomingMovies(pageNo: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.UPCOMING_MOVIES.getUrl(apiKey, pageNo)).body()

    override suspend fun getNowPlayingMovies(pageNo: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.NOW_PLAYING_MOVIES.getUrl(apiKey, pageNo)).body()

    override suspend fun getMovieDetails(movieId: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.MOVIE_DETAILS.getUrl(apiKey, movieId)).body()

    override suspend fun getSimilarMovies(movieId: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.SIMILAR_MOVIES.getUrl(apiKey, movieId)).body()

    override suspend fun getLatestMovie(page: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.LATEST_MOVIES.getUrl(apiKey, page)).body()

    override suspend fun searchMovies(query: String, pageNo: Int): MoviesDTO =
        httpClient.get(baseUrl + Endpoints.SEARCH_MOVIES.getUrl(apiKey, query, pageNo)).body()
}
