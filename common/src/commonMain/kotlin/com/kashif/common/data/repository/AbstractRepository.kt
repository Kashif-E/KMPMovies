package com.kashif.common.data.repository

import com.kashif.common.data.dto.MoviesDTO
import com.kashif.common.data.dto.PaginatedData
import com.kashif.common.data.remote.*

abstract class AbstractRepository {
    abstract suspend fun getPopularMovies(pageNo: Int): MoviesDTO
    abstract fun getLatesMoviesPagingSource(): LatestMoviesPagingSource
    abstract fun getUpcomingMoviesPagingSource(): NowPlayingMoviesPagingSource
    abstract fun getNowPlayingMoviesPagingSource(): PopularMoviesPagingSource
    abstract fun getPopularMoviesPagingSource(): TopRatedMoviesPagingSource
    abstract fun getTopRatedMoviesPagingSource(): UpcomingMoviesPagingSource
}
