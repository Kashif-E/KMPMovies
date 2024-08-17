package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.model.MoviesDomainModel

@Composable
fun MovieCardSmall(movie: MoviesDomainModel, onClick: () -> Unit) {
    val rememberedClick = remember { Modifier.clickable {
        onClick()
    }}
    Card(
        modifier =
            Modifier.size(width = 150.dp, height = 200.dp)
                .then(rememberedClick)
                .border(0.2.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .animateContentSize(),
        shape = RoundedCornerShape(8.dp),) {
            CachedAsyncImage(url = movie.posterPath, modifier = Modifier.fillMaxSize())
        }
}
