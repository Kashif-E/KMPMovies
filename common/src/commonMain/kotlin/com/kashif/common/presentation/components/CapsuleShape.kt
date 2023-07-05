package com.kashif.common.presentation.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable

@Composable
fun CapsuleShape() = RoundedCornerShape(
    topStart = ZeroCornerSize,
    topEnd = ZeroCornerSize,
    bottomStart = CornerSize(50),
    bottomEnd = CornerSize(50)
)
