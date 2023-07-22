package com.kashif.common.presentation.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.data.paging.Result
import com.kashif.common.presentation.components.MovieCard
import com.kashif.common.presentation.components.MovieCardSmall
import com.kashif.common.presentation.components.PagerMovieCard
import com.kashif.common.presentation.components.SearchAppBar
import com.kashif.common.presentation.components.placeHolderRow
import com.kashif.common.presentation.provide
import com.kashif.common.presentation.screens.videoPlayerScreen.VideoPlayerScreen
import com.kashif.common.presentation.theme.Grey
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(screenModel: HomeScreenViewModel = provide.screenModel) {
    val pagerList by screenModel.popularMovies.collectAsState()

    val popularMovies by screenModel.popularMoviesPaging.first.collectAsState()
    val topRatedMovies by screenModel.topRatedMovies.first.collectAsState()
    val upcomingMovies by screenModel.upcomingMovies.first.collectAsState()
    val nowPlayingMovies by screenModel.nowPlayingMoviesPaging.first.collectAsState()

    LaunchedEffect(Unit) { screenModel.onLaunch() }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
                Movies(
                    pagerList,
                    popularMovies,
                    topRatedMovies,
                    upcomingMovies,
                    nowPlayingMovies,
                    screenModel)
                SearchAppBar(
                    placeHolder = "Search for movies, TV shows, people...", onTextChange = {})
            }
        }
}

@Composable
fun Movies(
    pagerList: MoviesState,
    popularMovies: Result<List<MoviesDomainModel>>,
    topRatedMovies: Result<List<MoviesDomainModel>>,
    upcomingMovies: Result<List<MoviesDomainModel>>,
    nowPlayingMovies: Result<List<MoviesDomainModel>>,
    screenModel: HomeScreenViewModel,
    bottomSheetNavigator : BottomSheetNavigator = LocalBottomSheetNavigator.current
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        pager(pagerList, bottomSheetNavigator)
        moviesList(
            movies = popularMovies, title = "Popular Movies", onMovieClick = {}, seeAllClick = {})
        moviesList(
            movies = topRatedMovies, title = "Top Rated", onMovieClick = {}, seeAllClick = {})
        moviesList(
            movies = upcomingMovies,
            title = "Up Coming Movies",
            onMovieClick = {},
            seeAllClick = {})
        nowPlayingMovies(nowPlayingMovies, screenModel.nowPlayingMoviesPaging.second)
    }
}

@Composable
fun HorizontalScroll(
    movies: List<MoviesDomainModel>,
    heading: String,
    onMovieClick: (MoviesDomainModel) -> Unit,
    seeAllClick: () -> Unit
) {

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = heading,
                    style =
                        MaterialTheme.typography.h3.copy(
                            fontWeight = FontWeight.W400, color = Color.White))

                Text(
                    modifier =
                        Modifier.padding(12.dp).clip(MaterialTheme.shapes.large).clickable {
                            seeAllClick()
                        },
                    text = "See all",
                    style =
                        MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.W600, color = Grey))
            }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
                items(movies) { movie ->
                    MovieCardSmall(movie = movie, onClick = { onMovieClick(movie) })
                }
            }
    }
}

fun LazyListScope.pager(pagerList: MoviesState, bottomSheetNavigator: BottomSheetNavigator) {
    item {
        when (pagerList) {
            is MoviesState.Error -> {}
            MoviesState.Idle -> {
                // Text(text = "idle")
            }
            MoviesState.Loading -> {
                Text(text = "loading")
            }
            is MoviesState.Success -> {
                AutoScrollingHorizontalSlider(pagerList.movies.size) { page ->
                    val movie = pagerList.movies[page]
                    PagerMovieCard(movie =movie , onPlayClick = {
                        bottomSheetNavigator.show(VideoPlayerScreen(movie))
                    }, onDetailsClick = {})
                }
            }
        }
    }
}

fun LazyListScope.moviesList(
    movies: Result<List<MoviesDomainModel>>,
    title: String,
    onMovieClick: (MoviesDomainModel) -> Unit,
    seeAllClick: () -> Unit
) {
    when (movies) {
        is Result.Loading -> {
            placeHolderRow()
        }
        is Result.Error -> {
            item { Text(movies.exception) }
        }
        is Result.Success -> {
            item {
                HorizontalScroll(
                    movies = movies.data,
                    heading = title,
                    onMovieClick = onMovieClick,
                    seeAllClick = seeAllClick)
            }
        }
    }
}

fun LazyListScope.nowPlayingMovies(
    popularMovies: Result<List<MoviesDomainModel>>,
    loadMovies: () -> Unit
) {
    when (popularMovies) {
        is Result.Loading -> {
            placeHolderRow()
        }
        is Result.Error -> {
            item { Text(popularMovies.exception) }
        }
        is Result.Success -> {
            verticalMovieList(popularMovies.data, "Upcoming Movies", loadMovies::invoke)
        }
    }
}

fun LazyListScope.verticalMovieList(
    data: List<MoviesDomainModel>,
    title: String,
    loadMovies: () -> Unit
) {
    item {
        Text(
            text = title,
            style =
                MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.W700, color = Color.LightGray),
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            textAlign = TextAlign.Start)
    }
    items(data) { movie ->
        if (data.last() == movie) {
            loadMovies()
        }
        MovieCard(movie = movie, onClick = {})
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoScrollingHorizontalSlider(
    size: Int,
    delay: Long = 2000,
    animationDuration: Int = 2000,
    content: @Composable (page: Int) -> Unit
) {
    val pagerState = rememberPagerState(1)

    LaunchedEffect(key1 = pagerState) {
        while (true) {
            val nextPage = (pagerState.currentPage + 1) % size
            tween<Float>(durationMillis = animationDuration, easing = FastOutSlowInEasing)
            pagerState.animateScrollToPage(page = nextPage, pageOffset = 0f)
            delay(delay) // Adjust this value to control the time between auto-scrolls
        }
    }

    Box(
        modifier =
            Modifier.background(MaterialTheme.colors.background).fillMaxWidth().height(516.dp)) {
            HorizontalPager(
                state = pagerState, modifier = Modifier.align(Alignment.Center), count = size) {
                    page ->
                    content(page)
                }
        }
}
