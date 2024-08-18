package com.kashif.common.presentation.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kashif.common.data.paging.Result
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.presentation.components.MovieCard
import com.kashif.common.presentation.components.MovieCardSmall
import com.kashif.common.presentation.components.PagerMovieCard
import com.kashif.common.presentation.components.placeHolderRow
import com.kashif.common.presentation.screens.detailsScreen.DetailsScreen
import com.kashif.common.presentation.screens.trailerScreen.TrailerScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    screenModel: HomeScreenViewModel = koinInject(),
    bottomSheetNavigator: BottomSheetNavigator = LocalBottomSheetNavigator.current,
    navigator: Navigator = LocalNavigator.currentOrThrow
) {
    val pagerList by screenModel.popularMovies.collectAsState()
    var currentPagerSize by rememberSaveable { mutableStateOf(516) }
    var alphaValue by remember { mutableStateOf(1f) }


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                val newImgSize = currentPagerSize + delta
                val previousImgSize = currentPagerSize
                currentPagerSize = newImgSize.coerceIn(0, 516)
                alphaValue = (currentPagerSize / 516f).coerceIn(
                    0f, 1f
                )
                val consumed = currentPagerSize - previousImgSize
                return Offset(0f, consumed.toFloat())
            }
        }
    }

    val popularMovies by screenModel.popularMoviesPaging.first.collectAsState()
    val topRatedMovies by screenModel.topRatedMovies.first.collectAsState()
    val upcomingMovies by screenModel.upcomingMovies.first.collectAsState()
    val nowPlayingMovies by screenModel.nowPlayingMoviesPaging.first.collectAsState()

    LaunchedEffect(Unit) { screenModel.onLaunch() }

    Box(
        modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection)
            .consumeWindowInsets(WindowInsets.safeDrawing), contentAlignment = Alignment.TopStart
    ) {
        pager(
            pagerList = pagerList,
            onPlayClick = { movie -> bottomSheetNavigator.show(TrailerScreen(movie)) },
            onDetailsClick = { id, title ->
                navigateToWebViewScreen(
                    movieId = id, title = title, navigator = navigator
                )
            },
            height = currentPagerSize,
            alpha = alphaValue
        )

        Movies(
            modifier = Modifier.padding(top = currentPagerSize.dp),
            popularMovies = popularMovies,
            topRatedMovies = topRatedMovies,
            upcomingMovies = upcomingMovies,
            nowPlayingMovies = nowPlayingMovies,
            screenModel = screenModel
        )


    }
}


@Composable
fun Movies(
    popularMovies: Result<List<MoviesDomainModel>>,
    topRatedMovies: Result<List<MoviesDomainModel>>,
    upcomingMovies: Result<List<MoviesDomainModel>>,
    nowPlayingMovies: Result<List<MoviesDomainModel>>,
    screenModel: HomeScreenViewModel,
    navigator: Navigator = LocalNavigator.currentOrThrow,
    modifier: Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(0.dp)
    ) {

        moviesList(movies = popularMovies, title = "Popular Movies", onMovieClick = { movie ->
            navigateToWebViewScreen(
                movieId = movie.id, navigator = navigator, title = movie.title
            )
        }, seeAllClick = {})
        moviesList(movies = topRatedMovies, title = "Top Rated", onMovieClick = { movie ->
            navigateToWebViewScreen(
                movieId = movie.id, navigator = navigator, title = movie.title
            )
        }, seeAllClick = {})
        moviesList(movies = upcomingMovies, title = "Up Coming Movies", onMovieClick = { movie ->
            navigateToWebViewScreen(
                movieId = movie.id, navigator = navigator, title = movie.title
            )
        }, seeAllClick = {})
        nowPlayingMovies(
            popularMovies = nowPlayingMovies,
            loadMovies = screenModel.nowPlayingMoviesPaging.second,
            navigator = navigator
        )
    }
}

fun navigateToWebViewScreen(movieId: Int, navigator: Navigator, title: String) {
    navigator.push(DetailsScreen(movieId = movieId, title = title))
}


@Composable
fun HorizontalScroll(
    movies: List<MoviesDomainModel>,
    heading: String,
    onMovieClick: (MoviesDomainModel) -> Unit,
    seeAllClick: () -> Unit
) {
    val rememberedSeeAllClick = remember { Modifier.clickable { seeAllClick() } }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier, text = heading, style = MaterialTheme.typography.bodySmall
            )

            Text(
                modifier = Modifier.clip(MaterialTheme.shapes.large).then(rememberedSeeAllClick),
                text = "See all",
                style = MaterialTheme.typography.bodySmall
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(movies) { movie ->
                MovieCardSmall(movie = movie, onClick = { onMovieClick(movie) })
            }
        }
    }
}

@Composable
fun pager(
    pagerList: MoviesState,
    height: Int,
    alpha: Float,
    onPlayClick: (movie: MoviesDomainModel) -> Unit,
    onDetailsClick: (id: Int, title: String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(height.dp).alpha(alpha)
    ) {
        when (pagerList) {
            is MoviesState.Error -> {}
            MoviesState.Idle -> {
                // Text(text = "idle")
            }

            MoviesState.Loading -> {
                Text(text = "loading")
            }

            is MoviesState.Success -> {
                AutoScrollingHorizontalSlider(pagerList.movies.size, height = height) { page ->
                    val movie = pagerList.movies[page]
                    PagerMovieCard(movie = movie,
                        onPlayClick = { onPlayClick(movie) },
                        onDetailsClick = { onDetailsClick(movie.id, movie.title) })
                }
            }
        }

    }
}

@Composable
fun AutoScrollingHorizontalSlider(
    size: Int,
    delay: Long = 2000,
    animationDuration: Int = 2000,
    height: Int = 516,
    content: @Composable (page: Int) -> Unit
) {
    val pagerState =
        rememberPagerState(initialPage = 1, initialPageOffsetFraction = 0f, pageCount = {
            size
        })

    LaunchedEffect(key1 = pagerState) {
        while (true) {
            val nextPage = (pagerState.currentPage + 1) % size
            tween<Float>(durationMillis = animationDuration, easing = FastOutSlowInEasing)
            pagerState.animateScrollToPage(page = nextPage, pageOffsetFraction = 0f)
            delay(delay)
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().height(height.dp)
    ) {
        HorizontalPager(modifier = Modifier.align(Alignment.Center),
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            pageContent = { page ->
                content(page)
            })
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
                    seeAllClick = seeAllClick
                )
            }
        }
    }
}

fun LazyListScope.nowPlayingMovies(
    popularMovies: Result<List<MoviesDomainModel>>, loadMovies: () -> Unit, navigator: Navigator
) {
    when (popularMovies) {
        is Result.Loading -> {
            placeHolderRow()
        }

        is Result.Error -> {
            item { Text(popularMovies.exception) }
        }

        is Result.Success -> {
            verticalMovieList(
                data = popularMovies.data,
                title = "Upcoming Movies",
                loadMovies = loadMovies::invoke,
                navigator = navigator
            )
        }
    }
}

fun LazyListScope.verticalMovieList(
    data: List<MoviesDomainModel>, title: String, loadMovies: () -> Unit, navigator: Navigator
) {
    item {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
    }
    items(data) { movie ->
        if (data.last() == movie) {
            loadMovies()
        }
        MovieCard(modifier = Modifier.padding(horizontal = 16.dp), movie = movie, onClick = {
            navigator.push(DetailsScreen(movieId = movie.id, title = movie.title))
        })
    }
}




