package com.kashif.common.data.repository

import com.kashif.common.data.dto.MoviesDTO
import com.kashif.common.data.dto.VideoDTO
import com.kashif.common.data.remote.*

abstract class AbstractRepository {
    abstract suspend fun getPopularMovies(pageNo: Int): MoviesDTO
    abstract fun getLatestMoviesPagingSource(): LatestMoviesPagingSource
    abstract fun getUpcomingMoviesPagingSource(): NowPlayingMoviesPagingSource
    abstract fun getNowPlayingMoviesPagingSource(): PopularMoviesPagingSource
    abstract fun getPopularMoviesPagingSource(): TopRatedMoviesPagingSource
    abstract fun getTopRatedMoviesPagingSource(): UpcomingMoviesPagingSource
    abstract suspend fun getTrailerVideo(movieId: Int): VideoDTO
}
