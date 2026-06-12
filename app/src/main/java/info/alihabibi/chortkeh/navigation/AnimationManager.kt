package info.alihabibi.chortkeh.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import info.alihabibi.profile.ProfileGraphRoute
import kotlin.reflect.KClass

private val topLevelRoutes: List<KClass<*>> = listOf(
    Home::class,
    ProfileGraphRoute::class,
    Reminder::class,
    Report::class
)

private fun NavDestination.bottomNavItemIndex(): Int =
    topLevelRoutes.indexOfFirst { routeClass ->
        hierarchy.any { it.hasRoute(routeClass) }
    }

private fun AnimatedContentTransitionScope<NavBackStackEntry>.bottomNavSlideDirection(): SlideDirection {
    val from = initialState.destination.bottomNavItemIndex()
    val to = targetState.destination.bottomNavItemIndex()
    return if (to > from) SlideDirection.Left else SlideDirection.Right
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.isBottomNavTransition(): Boolean =
    initialState.destination.bottomNavItemIndex() != -1 && targetState.destination.bottomNavItemIndex() != -1


val bottomNavEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(bottomNavSlideDirection(), tween(300))
}

val bottomNavExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(bottomNavSlideDirection(), tween(300))
}

val pushEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(SlideDirection.Left, tween(300))
}

val pushExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(SlideDirection.Left, tween(300))
}

val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    scaleIn(
        initialScale = 0.92f,
        animationSpec = tween(400)
    ) + fadeIn(animationSpec = tween(450))
}

val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    scaleOut(
        targetScale = 0.85f,
        animationSpec = tween(400)
    ) + fadeOut(animationSpec = tween(450))
}