package com.kashif.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kashif.common.data.repository.AbstractRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
internal fun App(
    platform: String,
) {
    var text by remember { mutableStateOf("Hello, World!") }
    val repository: AbstractRepository =  Koiner.repo
        LaunchedEffect(Unit) {
            val repo = repository.getPopularMovies(1)

            text = repo.movies.toString()
        }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { text = "Hello, $platform" }) { Text(text) }
    }
}

object Koiner : KoinComponent {
    val repo : AbstractRepository = get()
}
