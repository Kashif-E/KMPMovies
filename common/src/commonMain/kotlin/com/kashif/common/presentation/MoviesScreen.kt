package com.kashif.common.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.paging.Result
import com.kashif.common.presentation.components.MovieCard
import com.kashif.common.presentation.components.MovieCardSmall
import com.kashif.common.presentation.components.PagerMovieCard
import com.kashif.common.presentation.components.PlaceHolderRow
import com.kashif.common.presentation.theme.Grey
import com.kashif.common.presentation.theme.SunnySideUp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(screenModel: HomeScreenViewModel = provide.screenModel) {
    val pagerList by screenModel.popularMovies.collectAsState()
    val latestMovies by screenModel.latestMovies.first.collectAsState()
    val popularMovies by screenModel.popularMoviesPaging.first.collectAsState()
    val topRatedMovies by screenModel.topRatedMovies.first.collectAsState()
    val upcomingMovies by screenModel.upcomingMovies.first.collectAsState()
    val nowPlayingMovies by screenModel.nowPlayingMoviesPaging.first.collectAsState()

    LaunchedEffect(Unit) { screenModel.onLaunch() }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {
            Movies(
                latestMovies,
                pagerList,
                popularMovies,
                topRatedMovies,
                upcomingMovies,
                nowPlayingMovies,
                screenModel)
        }
}

@Composable
fun Movies(
    latestMovies: Result<List<MoviesDomainModel>>,
    pagerList: MoviesState,
    popularMovies: Result<List<MoviesDomainModel>>,
    topRatedMovies: Result<List<MoviesDomainModel>>,
    upcomingMovies: Result<List<MoviesDomainModel>>,
    nowPlayingMovies: Result<List<MoviesDomainModel>>,
    screenModel: HomeScreenViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        pager(pagerList)
     //   latestMovies(latestMovies)
        popularMovies(popularMovies)
        topRatedMovies(topRatedMovies)
        upComingMovies(upcomingMovies)
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

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.Start) {
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
                                fontWeight = FontWeight.W700, color = Grey))
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

fun LazyListScope.pager(pagerList: MoviesState) {
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
                    PagerMovieCard(movie = pagerList.movies[page], onClick = {})
                }
            }
        }
    }
}

fun LazyListScope.latestMovies(latestMovies: Result<List<MoviesDomainModel>>) {
    when (latestMovies) {
        is Result.Loading -> {
            PlaceHolderRow()
        }
        is Result.Error -> {
            item { Text(latestMovies.exception) }
        }
        is Result.Success -> {
            item {
                HorizontalScroll(
                    movies = latestMovies.data,
                    heading = "Latest Movies",
                    onMovieClick = { movie -> println(movie.title) },
                    seeAllClick = {})
            }
        }
    }
}

fun LazyListScope.popularMovies(popularMovies: Result<List<MoviesDomainModel>>) {
    when (popularMovies) {
        is Result.Loading -> {
            PlaceHolderRow()
        }
        is Result.Error -> {
            item { Text(popularMovies.exception) }
        }
        is Result.Success -> {
            item {
                HorizontalScroll(
                    movies = popularMovies.data,
                    heading = "Popular Movies",
                    onMovieClick = { movie -> println(movie.title) },
                    seeAllClick = {})
            }
        }
    }
}

fun LazyListScope.topRatedMovies(popularMovies: Result<List<MoviesDomainModel>>) {
    when (popularMovies) {
        is Result.Loading -> {
            PlaceHolderRow()
        }
        is Result.Error -> {
            item { Text(popularMovies.exception) }
        }
        is Result.Success -> {
            item {
                HorizontalScroll(
                    movies = popularMovies.data,
                    heading = "Top Rated Movies",
                    onMovieClick = { movie -> println(movie.title) },
                    seeAllClick = {})
            }
        }
    }
}

fun LazyListScope.upComingMovies(popularMovies: Result<List<MoviesDomainModel>>) {
    when (popularMovies) {
        is Result.Loading -> {
            PlaceHolderRow()
        }
        is Result.Error -> {
            item { Text(popularMovies.exception) }
        }
        is Result.Success -> {
            item {
                HorizontalScroll(
                    movies = popularMovies.data,
                    heading = "Up Coming Movies",
                    onMovieClick = { movie -> println(movie.title) },
                    seeAllClick = {})
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
            PlaceHolderRow()
        }
        is Result.Error -> {
            item { Text(popularMovies.exception) }
        }
        is Result.Success -> {
            NowPlayingMovies(popularMovies.data, loadMovies::invoke)
        }
    }
}

fun LazyListScope.NowPlayingMovies(data: List<MoviesDomainModel>, loadMovies: () -> Unit) {
    item {
        Text(
            text = "Now Playing Movies",
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.W700),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start)
    }
    items(data) { movie ->
        if (data.last() == movie) {
            loadMovies()
        }
        MovieCard(movie = movie, onClick = {})
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Header() {

    var query by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier.fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.PlayCircleFilled,
                        contentDescription = "TMDb Logo",
                        tint = SunnySideUp,
                        modifier = Modifier.size(48.dp))
                    Text(
                        text = "Composable",
                        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.W500),
                        color = SunnySideUp)
                }
            Spacer(Modifier.height(32.dp))
            val keyboardController = LocalSoftwareKeyboardController.current
            OutlinedTextField(
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                value = query,
                shape = MaterialTheme.shapes.large,
                onValueChange = { search ->
                    if (search.contains("\n")) {
                        keyboardController?.hide()
                    } else query = search
                },
                placeholder = {
                    Text(
                        "Search for movies, TV shows, people...",
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.body2)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search-icon",
                        tint = MaterialTheme.colors.onSurface)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "clear-icon",
                            tint = MaterialTheme.colors.onSurface,
                            modifier =
                                Modifier.clickable {
                                    keyboardController?.hide()
                                    query = ""
                                })
                    }
                },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }))
        }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoScrollingHorizontalSlider(
    size: Int,
    delay: Long = 2000,
    animationDuration: Int = 1000,
    content: @Composable (page: Int) -> Unit
) {
    val pagerState = rememberPagerState(1)

    LaunchedEffect(key1 = pagerState) {
        while (true) {
            val nextPage = (pagerState.currentPage + 1) % size
            tween<Float>(durationMillis = animationDuration, easing = LinearEasing)
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
