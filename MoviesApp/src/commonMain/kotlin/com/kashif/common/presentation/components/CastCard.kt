package com.kashif.common.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.model.CastMemberDomainModel


@Composable
fun CastCard(castMember: CastMemberDomainModel, onClick: () -> Unit) {
    val rememberedClick = remember {
        Modifier.clickable {
            onClick()
        }
    }
    Column(
        modifier = Modifier.width(150.dp).height(220.dp).then(rememberedClick)
            .animateContentSize()
            .clip(MaterialTheme.shapes.large)
            .border(1.dp, Color.LightGray.copy(alpha = 0.8f), MaterialTheme.shapes.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        castMember.profilePath?.let {
            CachedAsyncImage(
                url = it, modifier = Modifier.fillMaxWidth().height(175.dp)
            )
        }
        Text(
            text = castMember.character,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
