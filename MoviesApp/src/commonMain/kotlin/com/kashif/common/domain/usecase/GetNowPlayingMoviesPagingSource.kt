package com.kashif.common.domain.usecase

import com.kashif.common.data.repository.AbstractRepository

class GetNowPlayingMoviesPagingSource(private val repository: AbstractRepository) {
    operator fun invoke() = repository.getNowPlayingMoviesPagingSource()
}