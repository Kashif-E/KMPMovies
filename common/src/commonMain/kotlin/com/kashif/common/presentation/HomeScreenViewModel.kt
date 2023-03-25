package com.kashif.common.presentation

import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.usecase.*
import com.kashif.common.domain.util.CustomMessage
import com.kashif.common.domain.util.Result
import com.kashif.common.domain.util.asResult
import com.kashif.common.ioDispatcher
import com.kashif.paging.PagingConfig
import com.kashif.common.paging.paginate
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val popularMoviesUseCase: GetPopularMoviesUseCase,
    private val latestMoviesUseCase: GetLatestMoviesPagingSource,
    private val upcomingMoviesUseCase: GetUpcomingMoviesPagingSource,
    private val topRatedMoviesUseCase: GetTopRatedMoviesPagingSource,
    private val popularMoviesPagingSource: GetPopularMoviesPagingSource,
    private val nowPlayingMoviesPagingSource: GetNowPlayingMoviesPagingSource,
) {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private val _popularMovies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val popularMovies = _popularMovies.asStateFlow()


    val latestMovies =
        viewModelScope.paginate(
            useCase = latestMoviesUseCase::invoke, pagingConfig = PagingConfig(20, 5))

    val upcomingMovies =
        viewModelScope.paginate(
            useCase = upcomingMoviesUseCase::invoke, pagingConfig = PagingConfig(20, 5))

    val topRatedMovies =
        viewModelScope.paginate(
            useCase = topRatedMoviesUseCase::invoke, pagingConfig = PagingConfig(20, 5))

    val popularMoviesPaging =
        viewModelScope.paginate(
            useCase = popularMoviesPagingSource::invoke, pagingConfig = PagingConfig(20, 5))

    val nowPlayingMoviesPaging =
        viewModelScope.paginate(
            useCase = nowPlayingMoviesPagingSource::invoke,
            pagingConfig = PagingConfig(20, Int.MAX_VALUE))

    fun onLaunch() {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        CoroutineScope(coroutineContextX).launch {
            popularMoviesUseCase().asResult().collectLatest { result ->
                when (result) {
                    is Result.Idle -> {
                        _popularMovies.update { MoviesState.Idle }
                    }
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

    fun onDispose() {
        job.cancel()
    }
}

sealed interface MoviesState {

    object Loading : MoviesState

    object Idle : MoviesState

    data class Success(val movies: List<MoviesDomainModel>) : MoviesState

    data class Error(val error: CustomMessage) : MoviesState
}
