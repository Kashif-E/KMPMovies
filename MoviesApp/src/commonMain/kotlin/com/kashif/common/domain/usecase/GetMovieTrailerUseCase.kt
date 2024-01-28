package com.kashif.common.domain.usecase

import com.kashif.common.data.remote.dto.asDomainModel
import com.kashif.common.data.repository.AbstractRepository
import kotlinx.coroutines.flow.flow

class GetMovieTrailerUseCase(
    private val repository: AbstractRepository
) {
    operator fun invoke(movieId: Int) = flow {
        emit(repository.getTrailerVideo(movieId = movieId).asDomainModel())
    }
}
