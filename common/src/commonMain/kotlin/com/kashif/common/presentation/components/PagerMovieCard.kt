package com.kashif.common.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.presentation.theme.Grey

@Composable
fun PagerMovieCard(modifier: Modifier= Modifier,movie: MoviesDomainModel, onPlayClick: () -> Unit, onDetailsClick : () -> Unit) {

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Box {
            AsyncImage(
                url = movie.hdPosterPath,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)

            Box(
                modifier =
                    Modifier.fillMaxSize()
                        .background(
                            brush =
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, MaterialTheme.colors.background),
                                    startY = 0.4f * 280.dp.value),
                        ),
                contentAlignment = Alignment.BottomStart) {
                    Row(
                        modifier =
                            Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                            CapsuleButton(
                                modifier = Modifier.height(30.dp).width(80.dp),
                                backgroundColor = Grey,
                                contentColor = Color.LightGray,
                                onClick = onPlayClick,
                                content = {
                                    Icon(
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        imageVector = Icons.Rounded.PlayArrow,
                                        contentDescription = "Play",
                                        tint = Color.LightGray,
                                    )
                                    Text(
                                        text = "Play",
                                        style =
                                            MaterialTheme.typography.h6.copy(
                                                color = Color.LightGray),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.CenterVertically))
                                },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CapsuleButton(
                                modifier = Modifier.height(30.dp).width(80.dp),
                                backgroundColor = MaterialTheme.colors.background,
                                contentColor = Color.LightGray,
                                borderStroke = BorderStroke(1.dp, Color.LightGray),
                                onClick = onDetailsClick,
                                content = {
                                    Text(
                                        modifier =
                                            Modifier.padding(horizontal = 2.dp)
                                                .fillMaxSize()
                                                .wrapContentSize(Alignment.Center),
                                        text = "Details",
                                        style =
                                            MaterialTheme.typography.h6.copy(
                                                color = Color.LightGray),
                                        textAlign = TextAlign.Center)
                                },
                            )
                        }
                }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun CapsuleButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    contentColor: Color,
    borderStroke: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit,
    onClick: () -> Unit
) {

    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(50.dp),
        content = content,
        onClick = onClick,
        border = borderStroke,
        contentPadding = PaddingValues(0.dp),
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor, contentColor = contentColor))
}
