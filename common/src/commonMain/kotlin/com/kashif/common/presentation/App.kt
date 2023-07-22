package com.kashif.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.kashif.common.presentation.components.SlideTransition
import com.kashif.common.presentation.screens.home.HomeScreen
import com.kashif.common.presentation.screens.home.HomeScreenViewModel
import com.kashif.common.presentation.theme.GreenSecondary
import com.kashif.common.presentation.theme.MoviesAppTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// val homeTab =HomeTab()
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun App() {

    MoviesAppTheme {

        // TabNavigator(homeTab) {
        BottomSheetNavigator(
            modifier = Modifier.animateContentSize(),
            sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            skipHalfExpanded = true) {
                Navigator(Application()) { navigator -> SlideTransition(navigator) }
            }
        // }
    }
}

class Application : Screen {

    @Composable
    override fun Content() {

        Scaffold(
            modifier = Modifier,
            scaffoldState = rememberScaffoldState(),
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier,
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = GreenSecondary,
                    elevation = 4.dp,
                ) {
                    // TabNavigationItem(tab = homeTab)
                    //                        TabNavigationItem(tab = CategoryTab)
                    //                        TabNavigationItem(tab = SearchTab)
                    //                        TabNavigationItem(tab = OrdersTab)
                    //                        TabNavigationItem(tab = ProfileTab)
                }
            },
        ) {
            HomeScreen()
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val title = tab.options.title
    BottomNavigationItem(
        modifier = Modifier,
        unselectedContentColor = MaterialTheme.colors.onSurface,
        selectedContentColor = MaterialTheme.colors.secondary,
        alwaysShowLabel = true,
        label = {
            Text(
                text = title,
            )
        },
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) })
}

object provide : KoinComponent {
    val screenModel by inject<HomeScreenViewModel>()
}
