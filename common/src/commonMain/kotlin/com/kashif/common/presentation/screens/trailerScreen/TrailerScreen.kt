package com.kashif.common.presentation.screens.trailerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.kashif.common.VideoPlayer
import com.kashif.common.data.paging.Result
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.model.VideoDomainModel
import com.kashif.common.presentation.components.CircularProgressbarAnimated
import com.kashif.common.presentation.components.RatingRow
import com.kashif.common.presentation.components.TransparentIconHolder
import com.kashif.common.presentation.theme.SunnySideUp
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TrailerScreen(private val movie: MoviesDomainModel) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        MainScreen(bottomSheetNavigator = LocalBottomSheetNavigator.current, movie = movie)
    }

    @Composable
    private fun MainScreen(
        bottomSheetNavigator: BottomSheetNavigator,
        movie: MoviesDomainModel,
        screenModel: TrailerScreenViewModel = get()
    ) {
        LaunchedEffect(this.movie) { screenModel.getTrailer(movieId = movie.id) }

        val state by screenModel.trailer.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.padding(12.dp).wrapContentHeight().fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically) {
                        TransparentIconHolder(
                            icon = Icons.Rounded.Close, onClick = { bottomSheetNavigator.hide() })
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.h5.copy(color = Color.White))
                    }

                when (state) {
                    is Result.Error -> {
                        Text((state as Result.Error).exception, modifier = Modifier.height(400.dp))
                    }
                    Result.Loading -> {
                        CircularProgressbarAnimated(
                            modifier =
                                Modifier.wrapContentHeight().align(Alignment.CenterHorizontally))
                    }
                    is Result.Success -> {
                        VideoPlayer(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .height(350.dp)
                                    .background(color = Color.Transparent),
                            (state as Result.Success<VideoDomainModel>).data.key)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)) {
                        Text(
                            text = "Release Date  ${movie.releaseDate}",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.h6.copy(color = SunnySideUp))
                        RatingRow(movie = movie, modifier = Modifier.fillMaxWidth())
                        Text(
                            text = "Prolog",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.h3.copy(color = Color.White))
                        Text(
                            text = movie.overview,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.W400))
                    }
            }
    }
}
