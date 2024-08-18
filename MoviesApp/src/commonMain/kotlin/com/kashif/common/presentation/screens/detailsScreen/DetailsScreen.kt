package com.kashif.common.presentation.screens.detailsScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SuggestionChip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kashif.common.VideoPlayer
import com.kashif.common.data.paging.Result
import com.kashif.common.domain.model.CastMemberDomainModel
import com.kashif.common.domain.model.MoviesDomainModel
import com.kashif.common.domain.model.VideoDomainModel
import com.kashif.common.presentation.components.CachedAsyncImage
import com.kashif.common.presentation.components.CastCard
import com.kashif.common.presentation.components.MovieCardSmall
import com.kashif.common.presentation.platform.MoviesAppScreen
import org.koin.compose.koinInject

class DetailsScreen(private val movieId: Int, private val title: String) : MoviesAppScreen {
    @Composable
    override fun Content() {
        MainScreen(movieId = movieId)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainScreen(
        viewModel: DetailsScreenViewModel = koinInject(),
        movieId: Int,
        navigator: Navigator = LocalNavigator.currentOrThrow,
    ) {
        LaunchedEffect(movieId) {
            viewModel.getDataAsynchronously(movieId)
        }
        val detailsState by viewModel.movieDetails.collectAsState()
        val trailerState by viewModel.trailer.collectAsState()
        val cast by viewModel.cast.collectAsState()

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = title, style = MaterialTheme.typography.titleSmall
                )
            }, navigationIcon = {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        navigator.pop()
                    })
            })
        }) { padding ->
            when (detailsState) {
                is Result.Error -> {
                    Text("Error")
                }

                Result.Loading -> {
                    Text("Loading")
                }

                is Result.Success -> {
                    MovieDetailsScreen(
                        modifier = Modifier.padding(padding),
                        movie = (detailsState as Result.Success<MoviesDomainModel>).data,
                        trailer = trailerState,
                        cast = cast
                    )
                }
            }
        }
    }

    @Composable
    fun MovieDetailsScreen(
        movie: MoviesDomainModel,
        modifier: Modifier,
        trailer: Result<VideoDomainModel>,
        cast: Result<List<CastMemberDomainModel>>
    ) {


        val animatedProgress = remember {
            Animatable(0f)
        }
        LaunchedEffect(animatedProgress) {
            animatedProgress.animateTo(
                movie.voteAverage / 10f, animationSpec = tween(durationMillis = 1000)
            )
        }

        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            header(movie = movie)
            detailsSection(movie = movie)
            watchTrailerOrDownload(trailerState = trailer)
            castSection(castState = cast)
        }

    }

    private fun LazyListScope.castSection(castState: Result<List<CastMemberDomainModel>>) {
        item {
            when (castState) {
                is Result.Error -> {
                    Text("Error")
                }

                Result.Loading -> {
                    Text("Loading")
                }

                is Result.Success -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "Cast",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(castState.data) { item ->
                                CastCard(
                                    castMember = item,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun LazyListScope.watchTrailerOrDownload(

        trailerState: Result<VideoDomainModel>
    ) {
        item {
            when (trailerState) {
                is Result.Error -> {
                    Text("Error")
                }

                Result.Loading -> {
                    Text("Loading")
                }

                is Result.Success -> {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Watch Trailer",
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1
                            )
                            Button(onClick = {}) {
                                Text("Download Poster", style = MaterialTheme.typography.labelSmall)
                            }
                        }

                        VideoPlayer(
                            modifier = Modifier.fillMaxWidth()
                                .height(250.dp).clip(MaterialTheme.shapes.large),
                            videoId = trailerState.data.key
                        )


                    }
                }
            }

        }
    }


    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
    private fun LazyListScope.detailsSection(movie: MoviesDomainModel) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        movie.genre.fastForEach {
                            SuggestionChip(shape = MaterialTheme.shapes.extraLarge, label = {
                                Text(
                                    text = it, style = MaterialTheme.typography.labelSmall.copy(

                                        fontWeight = W400
                                    ), maxLines = 1
                                )
                            }, onClick = {})
                        }

                    }

                }
                Text(
                    text = movie.releaseDate, style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray, fontWeight = W400
                    ), maxLines = 1
                )
                Text(
                    text = movie.overview, style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray, fontWeight = W400
                    ), maxLines = 4
                )
            }
        }
    }

    private fun LazyListScope.header(
        movie: MoviesDomainModel,
    ) {
        item {
            Box(
                Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(450.dp)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                CachedAsyncImage(
                    url = movie.hdPosterPath,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}