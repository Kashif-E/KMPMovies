package com.kashif.common.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
 fun AnimatedHeartIcon(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (isSelected: Boolean) -> Unit = {}
) {
    var isSelected by remember { mutableStateOf(selected) }
    val initialColor: Color = Color.Gray
    val selectedColor: Color = Color.Red
    val color by
    animateColorAsState(
        if (isSelected) selectedColor else initialColor,
        animationSpec = tween(durationMillis = 200)
    )

    IconButton(
        modifier = modifier,
        onClick = {
            isSelected = !isSelected
            onClick(isSelected)
        }) {
        Icon(
            imageVector =
            if (isSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = color)
    }
}