package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
                                .background(ShimmerBrush(showShimmer = true))
                                .animateContentSize(),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color.Transparent

                    ) {}
                }
            }
    }
}
