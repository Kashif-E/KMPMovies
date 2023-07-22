package com.kashif.common.domain.usecase

import com.kashif.common.data.dto.asDomainModel
import com.kashif.common.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow

class GetMovieTrailerUseCase(private val repository: AbstractRepository) {
    operator fun invoke(movieId: Int) = flow {
        val response = repository.getTrailerVideo(movieId = movieId)
        emit(response.asDomainModel())
    }
}
