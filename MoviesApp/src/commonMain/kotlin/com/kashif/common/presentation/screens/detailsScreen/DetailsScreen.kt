package com.kashif.common.presentation.screens.detailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kashif.common.data.paging.Result
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.presentation.components.CachedAsyncImage
import com.kashif.common.presentation.components.TransparentIconHolder
import com.kashif.common.presentation.platform.MoviesAppScreen
import com.kashif.common.presentation.screens.trailerScreen.TrailerScreen
import org.koin.compose.koinInject

class DetailsScreen(private val movieId: Int) : MoviesAppScreen {
    @Composable
    override fun Content() {
        MainScreen(movieId = movieId)
    }

    @Composable
    private fun MainScreen(
        viewModel: DetailsScreenViewModel = koinInject(),
        movieId: Int,
        navigator: Navigator = LocalNavigator.currentOrThrow,
        bottomSheetNavigator: BottomSheetNavigator = LocalBottomSheetNavigator.current
    ) {
        LaunchedEffect(movieId) {
            viewModel.getMovieDetails(movieId)
        }
        val detailsState by viewModel.movieDetails.collectAsState()

        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {
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
                        bottomSheetNavigator
                    )
                }
            }

            TransparentIconHolder(
                modifier = Modifier.wrapContentSize().align(Alignment.TopStart),
                icon = Icons.AutoMirrored.Rounded.ArrowBack
            ) { navigator.pop() }
        }
    }

    @Composable
    private fun Details(
        detailsState: MoviesDomainModel,
        navigator: Navigator = LocalNavigator.currentOrThrow,
        bottomSheetNavigator: BottomSheetNavigator = LocalBottomSheetNavigator.current
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CachedAsyncImage(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                url = detailsState.backdropPath
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(detailsState.title, style = MaterialTheme.typography.h4)
                Text("Overview", style = MaterialTheme.typography.h5)
                Text(detailsState.overview, style = MaterialTheme.typography.body2)
                Text("Release Date", style = MaterialTheme.typography.h5)
                Text(detailsState.releaseDate, style = MaterialTheme.typography.body2)

            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                CachedAsyncImage(
                    url = detailsState.hdPosterPath,
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )
                Text(
                    "Watch Trailer",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.clip(CircleShape).clickable {
                        bottomSheetNavigator.show(TrailerScreen(movie = detailsState))
                    })

            }


        }

    }
}
