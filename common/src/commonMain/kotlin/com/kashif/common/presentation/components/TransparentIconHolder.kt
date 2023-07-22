package com.kashif.common.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TransparentIconHolder(
    modifier: Modifier = Modifier.padding(12.dp).size(50.dp),
    icon: ImageVector = Icons.Rounded.Search,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier,
        shape = CircleShape,
        backgroundColor = Color.LightGray.copy(alpha = 0.5f),
        elevation = 0.dp) {
            IconButton(onClick = onClick) { Icon(imageVector = icon, "", tint = Color.White) }
        }
}
