package com.kashif.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.RowScope
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
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.kashif.common.presentation.components.SlideTransition
import com.kashif.common.presentation.tabs.HomeTab
import com.kashif.common.presentation.theme.DarkPH
import com.kashif.common.presentation.theme.GreenSecondary
import com.kashif.common.presentation.theme.LightPH
import com.kashif.common.presentation.theme.MoviesAppTheme

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
 fun App() {

    MoviesAppTheme {
        TabNavigator(HomeTab) {
            BottomSheetNavigator(
                modifier = Modifier.animateContentSize(),
                sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                skipHalfExpanded = true) {
                    Navigator(Application()) { navigator -> SlideTransition(navigator) }
                }
        }
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
                    TabNavigationItem(tab = HomeTab)
                    /* TabNavigationItem(tab = CategoryTab)
                    TabNavigationItem(tab = SearchTab)
                    TabNavigationItem(tab = OrdersTab)
                    TabNavigationItem(tab = ProfileTab)*/
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
        unselectedContentColor = DarkPH,
        selectedContentColor = LightPH,
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


