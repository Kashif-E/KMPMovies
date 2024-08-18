
package com.kashif.common.presentation.platform

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

//
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.EnterTransition
//import androidx.compose.animation.ExitTransition
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.AnimationSpec
//import androidx.compose.animation.core.FastOutSlowInEasing
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.TweenSpec
//import androidx.compose.animation.core.VisibilityThreshold
//import androidx.compose.animation.core.spring
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideIn
//import androidx.compose.animation.slideOut
//import androidx.compose.animation.togetherWith
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxWithConstraints
//import androidx.compose.foundation.layout.fillMaxSize
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.key
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithContent
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.platform.LocalLayoutDirection
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.zIndex
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.core.screen.ScreenKey
//import cafe.adriel.voyager.core.stack.StackEvent
//import cafe.adriel.voyager.navigator.Navigator
//import kotlinx.coroutines.launch
//import kotlin.experimental.ExperimentalObjCRefinement
//import kotlin.math.absoluteValue
//import kotlin.math.roundToInt
//import kotlin.native.HidesFromObjC
//
///**
// * Original code from here: https://github.com/adrielcafe/voyager/issues/144
// */
expect var shouldAllowSwiperSwiping: Boolean
//
//@OptIn(ExperimentalMaterialApi::class)
//class Swiper(
//    val slideInHorizontally: (fullWidth: Int) -> Int = { -it / 4 },
//    val spaceToSwipe: Dp = 20.dp,
//    val swipeThreshold: ThresholdConfig = FixedThreshold(150.dp),
//    val shadowColor: Color = Color.Black.copy(alpha = .65f),
//    val swipeAnimSpec: AnimationSpec<Float> = tween(),
//)
//
//
//@Composable
//@OptIn(ExperimentalMaterialApi::class)
//fun SwiperDoSwiping(navigator: Navigator) {
//    val swiper = remember { Swiper() }
//
//    val currentSceneEntry = navigator.lastItem
//    val prevSceneEntry = remember(navigator.items) { navigator.items.getOrNull(navigator.size - 2) }
//
//    var prevWasSwiped by remember {
//        mutableStateOf(false)
//    }
//
//    LaunchedEffect(currentSceneEntry) {
//        prevWasSwiped = false
//    }
//
//    val dismissState = key(currentSceneEntry) {
//        rememberDismissState()
//    }
//
//    LaunchedEffect(dismissState.isDismissed(DismissDirection.StartToEnd)) {
//        if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
//            prevWasSwiped = true
//            try {
//                (currentSceneEntry as MoviesAppScreen).backHandler?.invoke() ?: run {
//                    navigator.pop()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//    }
//
//    val showPrev by remember(dismissState) {
//        derivedStateOf {
//            dismissState.offset.value > 0f
//        }
//    }
//
//    val visibleItems = remember(currentSceneEntry, prevSceneEntry, showPrev) {
//        if (showPrev) {
//            listOfNotNull(currentSceneEntry, prevSceneEntry)
//        } else {
//            listOfNotNull(currentSceneEntry)
//        }
//    }
//
//    val canGoBack = remember(navigator.size) { navigator.size > 1 }
//
//    val animationSpec = remember {
//        spring(
//            stiffness = Spring.StiffnessMediumLow,
//            visibilityThreshold = IntOffset.VisibilityThreshold
//        )
//    }
//
//    // display visible items using SwipeItem
//    visibleItems.forEachIndexed { index, backStackEntry ->
//        val isPrev = remember(index, visibleItems.size) {
//            index == 1 && visibleItems.size > 1
//        }
//        AnimatedContent(
//            backStackEntry,
//            transitionSpec = {
//                if (prevWasSwiped) {
//                    EnterTransition.None togetherWith ExitTransition.None
//                } else {
//                    val (initialOffset, targetOffset) = when (navigator.lastEvent) {
//                        StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
//                        else -> ({ size: Int -> size }) to ({ size: Int -> -size })
//                    }
//
//                    slideInTransition() togetherWith
//                            slideOutTransition()
//
//                }
//            },
//            modifier = Modifier.zIndex(
//                if (isPrev) {
//                    0f
//                } else {
//                    1f
//                },
//            ),
//        ) { screen ->
//            SwipeItem(
//                dismissState = dismissState,
//                swiper = swiper,
//                isPrev = isPrev,
//                isLast = !canGoBack,
//                isSwipeEnabled = (currentSceneEntry as MoviesAppScreen).swipeEnabled
//            ) {
//                println(dismissState.progress.fraction)
//                navigator.saveableState("swipe", screen) {
//                    AnimatedBlackEffectScreen({ screen.Content() }, dismissState.progress.fraction)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun AnimatedBlackEffectScreen(screenContent: @Composable () -> Unit, dismissStateProgress: Float) {
//    val coroutineScope = rememberCoroutineScope()
//
//    val initialAlpha = 0.45f
//    val alphaAnimatable = remember { Animatable(initialAlpha) }
//
//    LaunchedEffect(dismissStateProgress) {
//        coroutineScope.launch {
//
//            val targetAlpha = (1f - dismissStateProgress) * initialAlpha
//            alphaAnimatable.animateTo(
//                targetValue = targetAlpha,
//                animationSpec = TweenSpec(durationMillis = 300)
//            )
//        }
//    }
//
//    Box(modifier = Modifier.background(Color.Black.copy(alpha = alphaAnimatable.value))) {
//        screenContent()
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = alphaAnimatable.value))
//        )
//    }
//}
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//private fun SwipeItem(
//    dismissState: DismissState,
//    swiper: Swiper,
//    isPrev: Boolean,
//    isLast: Boolean,
//    modifier: Modifier = Modifier,
//    isSwipeEnabled: Boolean,
//    content: @Composable () -> Unit,
//) {
//    CustomSwipeToDismiss(
//        state = if (isPrev) rememberDismissState() else dismissState,
//        spaceToSwipe = swiper.spaceToSwipe,
//        enabled = !isLast,
//        dismissThreshold = swiper.swipeThreshold,
//        modifier = modifier,
//        shouldDisableSwipe = !isSwipeEnabled
//    ) {
//        Box(
//            modifier = Modifier
//                .takeIf { isPrev }
//                ?.graphicsLayer {
//                    translationX =
//                        swiper.slideInHorizontally(size.width.toInt())
//                            .toFloat() -
//                                swiper.slideInHorizontally(
//                                    dismissState.offset.value.absoluteValue.toInt(),
//                                )
//                }?.drawWithContent {
//                    drawContent()
//                    drawRect(
//                        swiper.shadowColor,
//                        alpha = (1f - dismissState.progress.fraction) *
//                                swiper.shadowColor.alpha,
//                    )
//                }?.pointerInput(0) {
//                    // prev entry should not be interactive until fully appeared
//                } ?: Modifier,
//        ) {
//            content.invoke()
//        }
//    }
//}
//
//
//@Composable
//@ExperimentalMaterialApi
//private fun CustomSwipeToDismiss(
//    state: DismissState,
//    enabled: Boolean = true,
//    spaceToSwipe: Dp = 10.dp,
//    modifier: Modifier = Modifier,
//    dismissThreshold: ThresholdConfig,
//    shouldDisableSwipe: Boolean = false,
//    dismissContent: @Composable () -> Unit,
//
//    ) = BoxWithConstraints(modifier) {
//    val width = constraints.maxWidth.toFloat()
//    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
//
//    val anchors = mutableMapOf(
//        0f to DismissValue.Default,
//        width to DismissValue.DismissedToEnd,
//    )
//
//    val shift = with(LocalDensity.current) {
//        remember(this, width, spaceToSwipe) {
//            (-width + spaceToSwipe.toPx().coerceIn(0f, width)).roundToInt()
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .swipeable(
//                state = state,
//                anchors = anchors,
//                thresholds = { _, _ -> dismissThreshold },
//                orientation = Orientation.Horizontal,
//                enabled = enabled && state.currentValue == DismissValue.Default && !shouldDisableSwipe, // Conditionally enable/disable swipe
//                reverseDirection = isRtl,
//                resistance = ResistanceConfig(
//                    basis = width,
//                    factorAtMin = SwipeableDefaults.StiffResistanceFactor,
//                    factorAtMax = SwipeableDefaults.StandardResistanceFactor,
//                ),
//            )
//            .graphicsLayer { translationX = state.offset.value },
//    ) {
//        dismissContent()
//    }
//}
//
//
//fun slideInTransition(): EnterTransition {
//    return slideIn(
//
//        initialOffset = { fullSize -> IntOffset(fullSize.width, 0) },
//
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioNoBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    ) + fadeIn(
//
//        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
//    )
//}
//
//
//fun slideOutTransition(): ExitTransition {
//    return slideOut(
//
//        targetOffset = { fullSize -> IntOffset(-fullSize.width, 0) },
//
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioNoBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    ) + fadeOut(
//
//        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
//    )
//}
interface MoviesAppScreen : Screen {


    var backHandler: (() -> Unit)?
        get() = null
        set(newBackHandler: (() -> Unit)?) {
            backHandler = newBackHandler
        }
    var swipeEnabled: Boolean
        get() = true
        set(value) {
            swipeEnabled = value
        }

    @Composable
    override fun Content()
}