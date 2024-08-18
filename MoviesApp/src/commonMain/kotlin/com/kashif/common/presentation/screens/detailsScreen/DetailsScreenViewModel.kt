package com.kashif.common.presentation.screens.detailsScreen


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.kashif.common.data.paging.Result
import com.kashif.common.data.paging.asResult
import com.kashif.common.domain.model.CastMemberDomainModel
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.model.VideoDomainModel
import com.kashif.common.domain.usecase.GetMovieCastUseCase
import com.kashif.common.domain.usecase.GetMovieDetailsUseCase
import com.kashif.common.domain.usecase.GetMovieTrailerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase
) : ScreenModel {

    private val _movieDetails: MutableStateFlow<Result<MoviesDomainModel>> =
        MutableStateFlow(Result.Loading)
    val movieDetails = _movieDetails.asStateFlow()
    private val _trailer = MutableStateFlow<Result<VideoDomainModel>>(Result.Loading)
    val trailer = _trailer.asStateFlow()
    private val _cast = MutableStateFlow<Result<List<CastMemberDomainModel>>>(Result.Loading)
    val cast = _cast.asStateFlow()

    fun getDataAsynchronously(movieId: Int) {
        screenModelScope.launch(Dispatchers.IO) {
            merge(
                getMovieCastUseCase(movieId).asResult(),
                getMovieDetailsUseCase(movieId).asResult(),
                getMovieTrailerUseCase(movieId).asResult()
            ).collect { result ->

                when (result) {
                    is Result.Error -> {
                        _movieDetails.value = Result.Error(result.exception)
                        _trailer.value = Result.Error(result.exception)
                        _cast.value = Result.Error(result.exception)
                    }

                    Result.Loading -> {
                        _movieDetails.value = Result.Loading
                        _trailer.value = Result.Loading
                        _cast.value = Result.Loading
                    }

                    is Result.Success -> {
                        when (result.data) {
                            is MoviesDomainModel -> {
                                _movieDetails.value = Result.Success(result.data)
                            }

                            is VideoDomainModel -> {
                                _trailer.value = Result.Success(result.data)
                            }

                            is List<*> -> {
                                _cast.value =
                                    Result.Success(result.data as List<CastMemberDomainModel>)
                            }
                        }
                    }
                }
            }
        }
    }


}
