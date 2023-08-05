package com.kashif.common.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kashif.common.presentation.theme.SunnySideUp


@Composable
 fun ShimmerStar(
    isShimmering: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    color: Color = SunnySideUp,
    duration: Int = 4000,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by
    infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec =
        infiniteRepeatable(
            animation =
            tween(
                durationMillis = duration,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart)
    )

    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null,
        tint = if (isShimmering) color else color.copy(alpha = 0.2f),
        modifier =
        modifier.size(size).graphicsLayer {
            if (isShimmering) {
                this.alpha = alpha
            }
        })
}
