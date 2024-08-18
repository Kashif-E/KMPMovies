package com.kashif.common.presentation.screens.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kashif.common.data.paging.Result
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.presentation.components.MovieCardSmall
import com.kashif.common.presentation.components.SearchAppBar
import com.kashif.common.presentation.screens.detailsScreen.DetailsScreen
import org.koin.compose.koinInject


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchScreenViewModel: SearchScreenViewModel = koinInject(),
    navigator: Navigator = LocalNavigator.currentOrThrow
) {
    val topRatedMovies by searchScreenViewModel.topMovies.first.collectAsState()
    val nextFunction = searchScreenViewModel.topMovies.second
    val data = remember {
        mutableListOf<MoviesDomainModel>()
    }
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {

        var searchText by remember { mutableStateOf("") }

        when (topRatedMovies) {
            is Result.Error -> {
                Text("error")
            }

            Result.Loading -> {
                Text("Loading")
            }

            is Result.Success -> {
                data.addAll((topRatedMovies as Result.Success<List<MoviesDomainModel>>).data)
            }
        }

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalItemSpacing = 8.dp
        ) {

            items(data) { item ->
                if (item == data.last()) {
                    nextFunction()
                }
                MovieCardSmall(item) {
                    navigator.push(DetailsScreen(movieId = item.id, title = item.title))
                }
            }
        }

        SearchAppBar(
            placeHolder = "Search for movies, TV shows, people...",
            onTextChange = { text ->
                searchText = text
            }) {

        }
    }
}