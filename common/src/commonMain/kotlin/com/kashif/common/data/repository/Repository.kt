package com.kashif.common.data.repository

import com.kashif.common.data.remote.*

class Repository(private val ktorService: AbstractKtorService) : AbstractRepository() {

    override suspend fun getPopularMovies(pageNo: Int) =
        ktorService.getPopularMovies(pageNo = pageNo)

    override  fun getLatesMoviesPagingSource(): LatestMoviesPagingSource = LatestMoviesPagingSource(ktorService)
    override  fun getUpcomingMoviesPagingSource(): NowPlayingMoviesPagingSource = NowPlayingMoviesPagingSource(ktorService)
    override  fun getNowPlayingMoviesPagingSource(): PopularMoviesPagingSource = PopularMoviesPagingSource(ktorService)
    override  fun getPopularMoviesPagingSource(): TopRatedMoviesPagingSource= TopRatedMoviesPagingSource(ktorService)
    override  fun getTopRatedMoviesPagingSource(): UpcomingMoviesPagingSource = UpcomingMoviesPagingSource(ktorService)


}
