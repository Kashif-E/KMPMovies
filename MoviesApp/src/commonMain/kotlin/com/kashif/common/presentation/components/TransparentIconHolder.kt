package com.kashif.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TransparentIconHolder(
    modifier: Modifier = Modifier.padding(12.dp).size(50.dp),
    icon: ImageVector = Icons.Rounded.Search,
    onClick: () -> Unit,
) {

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),CircleShape), contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) { Icon(imageVector = icon, "", tint = Color.White) }
    }
}
