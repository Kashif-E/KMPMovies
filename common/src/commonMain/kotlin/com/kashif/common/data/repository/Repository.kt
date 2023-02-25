package com.kashif.common.data.repository

import com.kashif.common.data.remote.AbstractKtorService

class Repository(private val ktorService: AbstractKtorService) : AbstractRepository() {

    override suspend fun getPopularMovies(pageNo: Int) =
        ktorService.getPopularMovies(pageNo = pageNo)
}
