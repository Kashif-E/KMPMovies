package com.kashif.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.RowScope
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
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.kashif.common.presentation.components.SlideTransition
import com.kashif.common.presentation.tabs.HomeTab
import com.kashif.common.presentation.theme.AppShapes
import com.kashif.common.presentation.theme.DarkColorPallete
import com.kashif.common.presentation.theme.GreenSecondary
import com.kashif.common.presentation.theme.Typography
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
internal fun App() {

    MaterialTheme(
        colors = DarkColorPallete,
        typography = Typography,
        shapes = AppShapes,
        content = {
            TabNavigator(HomeTab) {
                BottomSheetNavigator {
                    Navigator(Application()) { navigator -> SlideTransition(navigator) }
                }
            }
        })
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
                    TabNavigationItem(tab = HomeTab)
                    //                        TabNavigationItem(tab = CategoryTab)
                    //                        TabNavigationItem(tab = SearchTab)
                    //                        TabNavigationItem(tab = OrdersTab)
                    //                        TabNavigationItem(tab = ProfileTab)
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
    BottomNavigationItem(
        modifier = Modifier,
        unselectedContentColor = MaterialTheme.colors.onSurface,
        selectedContentColor = MaterialTheme.colors.secondary,
        alwaysShowLabel = true,
        label = {
            Text(
                text = tab.options.title,
            )
        },
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) })
}

object provide : KoinComponent {
    val screenModel by inject<HomeScreenViewModel>()
}
