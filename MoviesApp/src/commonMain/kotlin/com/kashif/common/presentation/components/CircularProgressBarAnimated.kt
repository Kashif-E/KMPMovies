package com.kashif.common.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kashif.common.presentation.theme.SunnySideUp
import kotlinx.coroutines.delay

@Composable
fun CircularProgressbarAnimated(
    modifier: Modifier = Modifier,
    numberStyle: TextStyle = MaterialTheme.typography.h2,
    size: Dp = 120.dp,
    thickness: Dp = 16.dp,
    animationDuration: Int = 3000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = SunnySideUp,
    backgroundIndicatorColor: Color = foregroundIndicatorColor.copy(alpha = 0.5f),
    extraSizeForegroundIndicator: Dp = 12.dp
) {
    var numberR by remember { mutableStateOf(0f) }

    // Number Animation
    val animateNumber =
        animateFloatAsState(
            targetValue = numberR,
            animationSpec =
            tween(durationMillis = animationDuration, delayMillis = animationDelay)
        )

    // Start the animation loop when the composable is first displayed
    LaunchedEffect(Unit) {
        while (true) {
            numberR = 100f // Animate to 100
            delay(animationDuration.toLong())
            numberR = 0f // Animate back to 0
            delay(animationDuration.toLong())
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(size = size)) {
        Canvas(modifier = Modifier.size(size = size)) {

            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
            )

            val sweepAngle = (animateNumber.value / 100) * 360

            // Foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style =
                Stroke(
                    (thickness + extraSizeForegroundIndicator).toPx(),
                    cap = StrokeCap.Butt)
            )
        }

        // Text that shows number inside the circle
        Text(text = (animateNumber.value).toInt().toString(), style = numberStyle)
    }

    Spacer(modifier = Modifier.height(32.dp))
}