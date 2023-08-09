package com.kashif.common.presentation.screens.detailsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kashif.common.data.paging.Result
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.presentation.components.TransparentIconHolder
import org.koin.compose.koinInject

class DetailsScreen(private val movieId: Int) : Screen {
    @Composable
    override fun Content() {
        MainScreen(viewModel = koinInject(), movieId = movieId)
    }

    @Composable
    private fun MainScreen(
        viewModel: DetailsScreenViewModel,
        movieId: Int,
        navigator: Navigator = LocalNavigator.currentOrThrow,
        bottomSheetNavigator: BottomSheetNavigator = LocalBottomSheetNavigator.current
    ) {
        val detailsState by viewModel.movieDetails.collectAsState()
        LaunchedEffect(movieId){
            viewModel.getMovieDetails(movieId)
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when (detailsState) {
                is Result.Error -> {
                    Text("Error")
                }
                Result.Loading -> {
                    Text("Loading")
                }
                is Result.Success -> {
                    Details(
                        (detailsState as Result.Success<MoviesDomainModel>).data,
                        navigator,
                        bottomSheetNavigator)
                }
            }

            TransparentIconHolder(modifier = Modifier.align(Alignment.TopStart), icon = Icons.Rounded.ArrowBack) { navigator.pop() }
        }
    }

    @Composable
    private fun Details(
        detailsState: MoviesDomainModel,
        navigator: Navigator,
        bottomSheetNavigator: BottomSheetNavigator
    ) {

    }
}
