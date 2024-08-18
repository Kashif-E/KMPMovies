package com.kashif.common.domain.usecase

import com.kashif.common.data.remote.dto.toDomainModel
import com.kashif.common.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow

class GetMovieCastUseCase(private val repository: AbstractRepository) {

    operator fun invoke(movieId: Int) = flow {
        val response = repository.getMovieCast(movieId)
        emit(response.toDomainModel())
    }
}