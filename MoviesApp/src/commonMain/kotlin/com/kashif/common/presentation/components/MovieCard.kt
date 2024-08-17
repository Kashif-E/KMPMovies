package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
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
    val rememberedClick = remember { Modifier.clickable { onClick() } }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth().animateContentSize()
            .clip(CardDefaults.shape).then(rememberedClick),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.65f)),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            CachedAsyncImage(
                url = movie.posterPath,
                modifier = Modifier.width(130.dp),
            )

            Box {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movie.releaseDate,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        RatingRow(movie = movie)
                    }



                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun RatingRow(modifier: Modifier = Modifier, movie: MoviesDomainModel) {

    val ratingColor = getRatingColor(movie.voteAverage)
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(animatedProgress) {
        animatedProgress.animateTo(
            movie.voteAverage / 10f, animationSpec = tween(durationMillis = 1000)
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(28.dp).clip(CircleShape).background(ratingColor).padding(2.dp)
        ) {
            Text(
                text = movie.voteAverage.toString(),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.surface),
                modifier = Modifier.align(Alignment.Center)
            )
        }


        CircularProgressIndicator(
            progress = { animatedProgress.value },
            modifier = Modifier.size(24.dp),
            color = ratingColor,
            strokeWidth = 4.dp,
            trackColor = Color.DarkGray
        )


        ShimmerStar(
            isShimmering = true,
            modifier = Modifier.size(24.dp),
            color = ratingColor
        )

        Text(
            text = movie.voteCount,
            style = MaterialTheme.typography.bodySmall,
        )
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
