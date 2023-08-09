package com.kashif.common.data.repository

import com.kashif.common.data.remote.dto.VideoDTO
import com.kashif.common.data.remote.*
import com.kashif.common.data.remote.dto.MoviesDTO

class Repository(private val ktorService: AbstractKtorService) : AbstractRepository() {

    override suspend fun getPopularMovies(pageNo: Int) =
        ktorService.getPopularMovies(pageNo = pageNo)

    override  fun getLatestMoviesPagingSource(): LatestMoviesPagingSource = LatestMoviesPagingSource(ktorService)
    override  fun getUpcomingMoviesPagingSource(): NowPlayingMoviesPagingSource = NowPlayingMoviesPagingSource(ktorService)
    override  fun getNowPlayingMoviesPagingSource(): PopularMoviesPagingSource = PopularMoviesPagingSource(ktorService)
    override  fun getPopularMoviesPagingSource(): TopRatedMoviesPagingSource= TopRatedMoviesPagingSource(ktorService)
    override  fun getTopRatedMoviesPagingSource(): UpcomingMoviesPagingSource = UpcomingMoviesPagingSource(ktorService)
    override suspend fun getTrailerVideo(movieId: Int) : VideoDTO = ktorService.getTrailerVideos(movieId)
    override suspend fun getMovieDetails(id: Int): MoviesDTO {
        ktorService.getMovieDetails(id)
        return MoviesDTO(emptyList())
    }


}
