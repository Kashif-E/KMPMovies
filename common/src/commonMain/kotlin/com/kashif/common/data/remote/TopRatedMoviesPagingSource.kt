package com.kashif.common.data.remote

import com.kashif.common.data.remote.dto.asDomainModel
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.data.paging.PagingSource

class TopRatedMoviesPagingSource(private val ktorService: AbstractKtorService) :
    PagingSource<Int, MoviesDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesDomainModel> {

        return try {
            val page = params.key ?: 1
            val response = ktorService.getTopRatedMovies(page)
            val movies = response.movies.asDomainModel()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (movies.isEmpty()) null else page + 1
            LoadResult.Success(movies, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
