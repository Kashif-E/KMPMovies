package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif.common.domain.model.MoviesDomainModel

@Composable
fun MovieCard(movie: MoviesDomainModel, onClick: () -> Unit) {

    Card(
        modifier =
            Modifier.fillMaxWidth()
                .clickable { onClick() }
                .animateContentSize()
                .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background,
        border = BorderStroke(width = 0.2.dp, color = Color.White)) {
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
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
}

@Composable
fun RatingRow(modifier: Modifier = Modifier,movie: MoviesDomainModel) {

    val ratingColor = getRatingColor(movie.voteAverage)

    Row(verticalAlignment = Alignment.CenterVertically, modifier =modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier.size(28.dp).clip(CircleShape).background(ratingColor).padding(2.dp)) {
                Text(
                    text = movie.voteAverage.toString(),
                    style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.background),
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
            color = Color.LightGray)
    }
}

@Composable
fun getRatingColor(rating: Float): Color {
    val green = Color(0xFF00C853)
    val yellow = Color(0xFFFFD600)
    val red = Color(0xFFFF1744)

    return when (rating) {
        in 0.0..5.0 -> red
        in 5.0..7.0 -> yellow
        else -> green
    }
}
