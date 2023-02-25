package com.kashif.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.usecase.GetPopularMoviesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
internal fun App(screenModel: MoviesScreenModel) {

    LaunchedEffect(Unit) { screenModel.onLaunch() }

    DisposableEffect(Unit) { onDispose { screenModel.onDispose() } }

    val moviesState by screenModel.popularMovies.collectAsState()

    when (moviesState) {
        is MoviesState.Idle -> {
            Text("idle")
        }
        is MoviesState.Error -> {
            Text((moviesState as MoviesState.Error).error.message)
        }
        is MoviesState.Loading -> {
            Text("Loding")
        }
        is MoviesState.Success -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(200.dp)) {
                    items((moviesState as MoviesState.Success).movies) { Text(it.title) }
                }
        }
    }
}

