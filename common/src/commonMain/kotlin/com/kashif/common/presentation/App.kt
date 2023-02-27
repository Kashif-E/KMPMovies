package com.kashif.common.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.presentation.components.AnimatedHeartIcon
import com.kashif.common.presentation.components.AsyncImage
import com.kashif.common.presentation.components.ShimmerStar
import com.kashif.common.presentation.theme.AppShapes
import com.kashif.common.presentation.theme.DarkColorPallete
import com.kashif.common.presentation.theme.SunnySideUp
import com.kashif.common.presentation.theme.Typography
import kotlinx.coroutines.delay

@Composable
internal fun App(screenModel: MoviesScreenModel) {

    LaunchedEffect(Unit) { screenModel.onLaunch() }

    DisposableEffect(Unit) { onDispose { screenModel.onDispose() } }

    val moviesState by screenModel.popularMovies.collectAsState()
    MaterialTheme(
        colors = DarkColorPallete,
        typography = Typography,
        shapes = AppShapes,
        content = { MoviesScreen(moviesState) })
}

@Composable
internal fun MoviesScreen(moviesState: MoviesState) {

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
            Spacer(Modifier.height(32.dp))
            Header()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                contentPadding = PaddingValues(16.dp),
            ) {
                when (moviesState) {
                    is MoviesState.Idle -> {
                        item { Text("idle") }
                    }
                    is MoviesState.Error -> {
                        item { Text(moviesState.error.message) }
                    }
                    is MoviesState.Loading -> {
                        item { Text("Loading") }
                    }
                    is MoviesState.Success -> {
                        items(moviesState.movies) { item -> MovieCard(item) {} }
                    }
                }
            }
        }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Header() {

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
                        style = MaterialTheme.typography.h1.copy(fontWeight = W500),
                        color = SunnySideUp)
                }
            Spacer(Modifier.height(32.dp))
            OutlinedTextField(
                value = query,
                shape = MaterialTheme.shapes.large,
                onValueChange = { movie -> query = movie },
                placeholder = {
                    Text(
                        "Search for movies, TV shows, people...",
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.body2)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.onSurface)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }

}

@Composable
internal fun MovieCard(movie: MoviesDomainModel, onClick: () -> Unit) {

    Card(
        modifier =
            Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }.animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.width(130.dp).fillMaxHeight()) {
                    AsyncImage(
                        url = movie.posterPath,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Box {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.h6,
                            fontSize = 18.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.releaseDate,
                            style = MaterialTheme.typography.caption,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(16.dp))
                        RatingRow(movie = movie)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.body2,
                            fontSize = 14.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis)
                    }
                    AnimatedHeartIcon(Modifier.align(Alignment.TopEnd).padding(8.dp)) {}
                }
            }
        }
}

@Composable
internal fun RatingRow(movie: MoviesDomainModel) {

    val ratingColor = getRatingColor(movie.voteAverage)

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier.size(28.dp).clip(CircleShape).background(ratingColor).padding(2.dp)) {
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.caption,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.Center))
            }
        Spacer(modifier = Modifier.width(8.dp))
        val animatedProgress = remember { Animatable(0f) }
        LaunchedEffect(animatedProgress) {
            animatedProgress.animateTo(
                movie.voteAverage / 10f, animationSpec = tween(durationMillis = 1000))
        }
        CircularProgressIndicator(
            progress = animatedProgress.value,
            color = ratingColor,
            strokeWidth = 4.dp,
            modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.width(8.dp))
        ShimmerStar(
            isShimmering = true,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = movie.voteCount,
            style = MaterialTheme.typography.caption,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f))
    }
}

@Composable
internal fun getRatingColor(rating: Float): Color {
    val green = Color(0xFF00C853)
    val yellow = Color(0xFFFFD600)
    val red = Color(0xFFFF1744)

    return when (rating) {
        in 0.0..5.0 -> red
        in 5.0..7.0 -> yellow
        else -> green
    }
}
