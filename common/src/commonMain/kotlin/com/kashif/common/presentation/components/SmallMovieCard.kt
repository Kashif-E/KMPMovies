package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.model.MoviesDomainModel

@Composable
fun MovieCardSmall(movie: MoviesDomainModel, onClick: () -> Unit) {
    Card(
        modifier =
        Modifier.height(200.dp).width(150.dp).clickable { onClick() }.animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(url = movie.posterPath, modifier = Modifier.fillMaxSize())
        }
    }
}
