package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.model.CastMemberDomainModel
import com.kashif.common.domain.model.MoviesDomainModel

@Composable
fun MovieCardSmall(movie: MoviesDomainModel, onClick: () -> Unit) {
    val rememberedClick = remember {
        Modifier.clickable {
            onClick()
        }
    }
    Card(
        modifier = Modifier.size(width = 150.dp, height = 200.dp).then(rememberedClick)
            .border(0.2.dp, Color.LightGray, RoundedCornerShape(8.dp)).animateContentSize(),
        shape = RoundedCornerShape(8.dp),
    ) {
        CachedAsyncImage(url = movie.posterPath, modifier = Modifier.fillMaxSize())
    }
}
