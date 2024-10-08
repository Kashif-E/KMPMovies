package com.kashif.common.data.remote

import com.kashif.common.data.remote.dto.MovieCreditsResponse
import com.kashif.common.data.remote.dto.MovieDetailsDTO
import com.kashif.common.data.remote.dto.MoviesDTO
import com.kashif.common.data.remote.dto.VideoDTO

abstract class AbstractKtorService {
    abstract suspend fun getPopularMovies(pageNo: Int): MoviesDTO
    abstract suspend fun getTopRatedMovies(pageNo: Int): MoviesDTO

    abstract suspend fun getUpcomingMovies(pageNo: Int): MoviesDTO

    abstract suspend fun getNowPlayingMovies(pageNo: Int): MoviesDTO

    abstract suspend fun getMovieDetails(movieId: Int): MovieDetailsDTO

    abstract suspend fun getSimilarMovies(movieId: Int): MoviesDTO
    abstract suspend fun getLatestMovie(page: Int): MoviesDTO
    abstract suspend fun searchMovies(query: String, pageNo: Int): MoviesDTO
    abstract suspend fun getTrailerVideos(movieId: Int): VideoDTO
    abstract suspend fun getMovieCast(movieId: Int): MovieCreditsResponse
}
