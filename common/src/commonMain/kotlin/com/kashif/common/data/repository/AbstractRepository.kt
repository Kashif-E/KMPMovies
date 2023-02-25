package com.kashif.common.data.repository

import com.kashif.common.data.dto.MoviesDTO

abstract class AbstractRepository {
    abstract suspend fun getPopularMovies(pageNo: Int): MoviesDTO
}