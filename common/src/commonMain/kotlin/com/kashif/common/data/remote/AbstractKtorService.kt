package com.kashif.common.data.remote

import com.kashif.common.data.dto.MoviesDTO

abstract class AbstractKtorService {
    abstract suspend fun getPopularMovies(pageNo: Int): MoviesDTO
}