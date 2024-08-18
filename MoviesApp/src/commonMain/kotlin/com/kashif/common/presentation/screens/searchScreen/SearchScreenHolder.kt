package com.kashif.common.presentation.screens.searchScreen


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.kashif.common.data.paging.Result
import com.kashif.common.data.paging.paginate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.usecase.GetTopRatedMoviesPagingSource
import com.kashif.paging.PagingConfig
import kotlinx.coroutines.IO
import kotlin.reflect.KFunction0


class SearchScreenViewModel(private val getTopRatedMoviesPagingSource: GetTopRatedMoviesPagingSource) :
    ScreenModel {

    private val _topMovies =screenModelScope.paginate(
        useCase = getTopRatedMoviesPagingSource::invoke, pagingConfig = PagingConfig(20, 10)
    )
    val topMovies: Pair<MutableStateFlow<Result<List<MoviesDomainModel>>>, KFunction0<Unit>> = _topMovies

    init {
        fetchTopMovies()
    }

    private fun fetchTopMovies() {
        screenModelScope.launch(Dispatchers.IO) {

        }
    }
}