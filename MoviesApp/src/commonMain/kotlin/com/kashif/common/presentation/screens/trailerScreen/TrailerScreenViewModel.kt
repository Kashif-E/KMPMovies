package com.kashif.common.presentation.screens.trailerScreen

import cafe.adriel.voyager.core.model.ScreenModel
import com.kashif.common.data.paging.Result
import com.kashif.common.data.paging.asResult
import com.kashif.common.domain.model.VideoDomainModel
import com.kashif.common.domain.usecase.GetMovieTrailerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrailerScreenViewModel(private val getMovieTrailerUseCase: GetMovieTrailerUseCase) :
    ScreenModel {
    private val _trailer = MutableStateFlow<Result<VideoDomainModel>>(Result.Loading)
    val trailer = _trailer.asStateFlow()

     fun getTrailer(movieId: Int) {
         CoroutineScope(Dispatchers.IO).launch {
             getMovieTrailerUseCase(movieId).asResult().collectLatest { result ->
                 _trailer.update { result }
             }
         }

    }
}
