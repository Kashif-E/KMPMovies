package com.kashif.common.domain.usecase

import com.kashif.common.data.remote.dto.asDomainModel
import com.kashif.common.data.repository.AbstractRepository
import com.kashif.common.data.repository.Repository
import com.kashif.common.domain.model.MoviesDomainModel
import kotlinx.coroutines.flow.flow

class GetPopularMoviesUseCase(private val repository: AbstractRepository) {
    private val movieList = mutableListOf<MoviesDomainModel>()
    private var pageNo = 1
    private var hasMorePages = true
    operator fun invoke() = flow {
        if (hasMorePages) {
            val response = repository.getPopularMovies(pageNo++).movies.asDomainModel()

            if (response.isEmpty()) {
                hasMorePages = false
            }
            movieList.addAll(response)
            emit(movieList)
        }
    }
}
