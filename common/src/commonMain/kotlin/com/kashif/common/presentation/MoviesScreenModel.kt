package com.kashif.common.presentation

import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.usecase.GetPopularMoviesUseCase
import com.kashif.common.domain.util.CustomMessage
import com.kashif.common.domain.util.Result
import com.kashif.common.domain.util.asResult
import com.kashif.common.ioDispatcher
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesScreenModel(private val popularMoviesUseCase: GetPopularMoviesUseCase) {

    private val _popularMovies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val popularMovies = _popularMovies.asStateFlow()
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + ioDispatcher

    fun onLaunch() {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        CoroutineScope(coroutineContext).launch {
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
