package com.kashif.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.kashif.common.domain.util.ChangeStatusBarColors
import com.kashif.common.presentation.components.SlideTransition
import com.kashif.common.presentation.components.getAsyncImageLoader
import com.kashif.common.presentation.platform.MoviesAppScreen
import com.kashif.common.presentation.tabs.HomeTab
import com.kashif.common.presentation.tabs.SavedMovies
import com.kashif.common.presentation.tabs.SearchTab
import com.kashif.common.presentation.theme.MoviesAppTheme

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalCoilApi::class, ExperimentalMaterialApi::class
)
@Composable
fun ComposeApp() {

    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    MoviesAppTheme {
        ChangeStatusBarColors(Color.Black)
        Box(
            modifier = Modifier.background(Color.Black).fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            TabNavigator(HomeTab) {
                BottomSheetNavigator(
                    modifier = Modifier.animateContentSize(),
                    sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    skipHalfExpanded = true
                ) {

                    Navigator(Application()) { navigator ->
                        SlideTransition(navigator)
//                        if (shouldAllowSwiperSwiping) {
//                            SwiperDoSwiping(navigator)
//                        } else {
//
//                        }
                    }
                }
            }

        }

    }
}

class Application : MoviesAppScreen {

    @Composable
    override fun Content() {
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.padding(12.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    tonalElevation = 12.dp,
                    containerColor = NavigationBarDefaults.containerColor.copy(alpha = 0.98f),
                    ) {
                    TabNavigationItem(tab = HomeTab)
                    TabNavigationItem(tab = SearchTab)
                    TabNavigationItem(tab = SavedMovies)
                }

            },
        ) {
            CurrentTab()
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val title = tab.options.title
    NavigationBarItem(
        modifier = Modifier,
        alwaysShowLabel = false,
        label = {
            Text(
                text = title, style = MaterialTheme.typography.bodySmall
            )
        },
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                modifier = Modifier.size(22.dp)
            )
        })
}


