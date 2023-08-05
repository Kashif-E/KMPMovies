package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.model.MoviesDomainModel

@Composable
fun MovieCardSmall(movie: MoviesDomainModel, onClick: () -> Unit) {
    Card(
        modifier =
            Modifier.size(width = 150.dp, height = 200.dp)
                .clickable { onClick() }
                .border(0.2.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.Transparent,
        elevation = 8.dp) {
            AsyncImage(url = movie.posterPath, modifier = Modifier.fillMaxSize())
        }
}
