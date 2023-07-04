package com.kashif.common.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator

typealias ScreenTransitionContent = @Composable AnimatedVisibilityScope.(Screen) -> Unit

@ExperimentalAnimationApi
@Composable
fun SlideTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    orientation: SlideOrientation = SlideOrientation.Horizontal,
    animationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow, visibilityThreshold = IntOffset.VisibilityThreshold
    ),
    content: ScreenTransitionContent = { it.Content() }
) {
    ScreenTransition(
        navigator = navigator, modifier = modifier, content = content,
        transition = {
            val (initialOffset, targetOffset) = when (navigator.lastEvent) {
                StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
                else -> ({ size: Int -> size }) to ({ size: Int -> -size })
            }

            when (orientation) {
                SlideOrientation.Horizontal -> slideInHorizontally(
                    animationSpec,
                    initialOffset
                ) with slideOutHorizontally(animationSpec, targetOffset)

                SlideOrientation.Vertical -> slideInVertically(
                    animationSpec,
                    initialOffset
                ) with slideOutVertically(animationSpec, targetOffset)
            }
        }
    )
}

enum class SlideOrientation {
    Horizontal, Vertical
}

@ExperimentalAnimationApi
@Composable
fun ScreenTransition(
    navigator: Navigator,
    enterTransition: AnimatedContentScope<Screen>.() -> ContentTransform,
    exitTransition: AnimatedContentScope<Screen>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    content: ScreenTransitionContent = { it.Content() }
) {
    ScreenTransition(
        navigator = navigator, modifier = modifier, content = content,
        transition = {
            when (navigator.lastEvent) {
                StackEvent.Pop -> exitTransition()
                else -> enterTransition()
            }
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun ScreenTransition(
    navigator: Navigator,
    transition: AnimatedContentScope<Screen>.() -> ContentTransform,
    modifier: Modifier = Modifier,
    content: ScreenTransitionContent = { it.Content() }
) {
    AnimatedContent(
        targetState = navigator.lastItem, transitionSpec = transition, modifier = modifier
    ) { screen ->
        navigator.saveableState("transition", screen) {
            content(screen)
        }
    }
}