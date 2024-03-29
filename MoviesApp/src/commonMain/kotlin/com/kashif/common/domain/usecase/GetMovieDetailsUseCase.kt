package com.kashif.common.domain.usecase

import com.kashif.common.data.remote.dto.asDomainModel
import com.kashif.common.data.repository.AbstractRepository
import com.kashif.common.domain.model.MoviesDomainModel
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(private val repository: AbstractRepository) {

    operator fun invoke(id: Int) = flow {
        val response = repository.getMovieDetails(id)
        emit(response.asDomainModel())
    }
}