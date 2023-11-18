package com.kashif.common.presentation.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import com.kashif.common.data.paging.*
import com.kashif.common.data.paging.asResult
import com.kashif.common.data.paging.paginate
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.usecase.*
import com.kashif.common.ioDispatcher
import com.kashif.paging.PagingConfig
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val latestMoviesUseCase: GetLatestMoviesPagingSource,
    private val upcomingMoviesUseCase: GetUpcomingMoviesPagingSource,
    private val topRatedMoviesUseCase: GetTopRatedMoviesPagingSource,
    private val popularMoviesPagingSource: GetPopularMoviesPagingSource,
    private val nowPlayingMoviesPagingSource: GetNowPlayingMoviesPagingSource,
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private val _popularMovies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val popularMovies = _popularMovies.asStateFlow()

    val latestMovies =
        viewModelScope.paginate(
            useCase = latestMoviesUseCase::invoke, pagingConfig = PagingConfig(20, 5)
        )

    val upcomingMovies =
        viewModelScope.paginate(
            useCase = upcomingMoviesUseCase::invoke, pagingConfig = PagingConfig(20, 5)
        )

    val topRatedMovies =
        viewModelScope.paginate(
            useCase = topRatedMoviesUseCase::invoke, pagingConfig = PagingConfig(20, 5)
        )

    val popularMoviesPaging =
        viewModelScope.paginate(
            useCase = popularMoviesPagingSource::invoke, pagingConfig = PagingConfig(20, 5)
        )

    val nowPlayingMoviesPaging =
        viewModelScope.paginate(
            useCase = nowPlayingMoviesPagingSource::invoke,
            pagingConfig = PagingConfig(20, Int.MAX_VALUE)
        )

    fun onLaunch() {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        if (_popularMovies.value !is MoviesState.Success) {
            viewModelScope.launch(Dispatchers.IO) {
                popularMoviesUseCase().asResult().collectLatest { result ->
                    when (result) {
                        is Result.Error -> {
                            _popularMovies.update { MoviesState.Error(result.exception) }
                        }

                        is Result.Loading -> {
                            _popularMovies.update { MoviesState.Loading }
                        }

                        is Result.Success -> {
                            _popularMovies.update { MoviesState.Success(result.data) }
                        }
                    }
                }
            }
        }

    }


}

sealed interface MoviesState {

    data object Loading : MoviesState

    data object Idle : MoviesState

    data class Success(val movies: List<MoviesDomainModel>) : MoviesState

    data class Error(val error: String) : MoviesState
}
