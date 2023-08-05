package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kashif.common.presentation.theme.DarkPH

@Composable
fun Modifier.placeHolder(color: Color, visibility: Boolean = true): Modifier =
    Modifier.background(shimmerBrush(targetValue = 1300f, showShimmer = visibility))

fun LazyListScope.placeHolderRow() {
    item {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
                items(12) {
                    Card(
                        modifier =
                            Modifier.height(200.dp)
                                .width(150.dp)
                                .placeHolder(DarkPH)
                                .animateContentSize()
                                .clip(RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color.Transparent) {}
                }
            }
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors =
            listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )

        val transition = rememberInfiniteTransition()
        val translateAnimation =
            transition.animateFloat(
                initialValue = 0f,
                targetValue = targetValue,
                animationSpec =
                    infiniteRepeatable(animation = tween(800), repeatMode = RepeatMode.Reverse))
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value))
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero)
    }
}
