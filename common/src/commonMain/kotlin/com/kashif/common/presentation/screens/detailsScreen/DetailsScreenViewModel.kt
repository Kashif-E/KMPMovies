package com.kashif.common.presentation.screens.detailsScreen


import cafe.adriel.voyager.core.model.ScreenModel
import com.kashif.common.data.paging.Result
import com.kashif.common.data.paging.asResult
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsScreenViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ScreenModel {

    private val _movieDetails: MutableStateFlow<Result<MoviesDomainModel>> =
        MutableStateFlow(Result.Loading)
    val movieDetails = _movieDetails.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            getMovieDetailsUseCase(movieId).asResult().collectLatest {}
        }
    }
}
