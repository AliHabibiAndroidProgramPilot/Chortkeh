package info.alihabibi.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import info.alihabibi.ui.navigation.AppBottomNavigation
import info.alihabibi.ui.navigation.BottomNavItem
import info.alihabibi.ui.scaffolds.BaseScaffold

@Composable
fun HomeDestination() {

    var selectedBottomNavItem by remember { mutableStateOf(BottomNavItem.HOME.name) }
    val navItems = BottomNavItem.entries.toList()

    BaseScaffold(
        bottomBar = {
            AppBottomNavigation(
                navItems = navItems,
                selectedNavItem = selectedBottomNavItem,
                onFabClick = { /*TODO(botton sheet)*/ },
                onNavItemClicked = { item -> selectedBottomNavItem = item.name }
            )
        }
    ) { paddingValues ->

            AnimatedContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                targetState = selectedBottomNavItem,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(220, delayMillis = 90)
                    ) + scaleIn(
                        initialScale = 0.98f,
                        animationSpec = tween(220, delayMillis = 90)
                    ) togetherWith
                            fadeOut(animationSpec = tween(90))
                }
            ) { bottomNavItem ->

                when (bottomNavItem) {
                    BottomNavItem.HOME.name -> HomeScreen()
                    BottomNavItem.PROFILE.name -> ProfileScreen()
                    BottomNavItem.REPORTS.name -> ReportsScreen()
                    BottomNavItem.REMINDER.name -> ReminderScreen()
                }

            }

    }

}

@Composable
private fun HomeScreen() {

    Text(text = "home", fontSize = 32.sp)

}

@Composable
private fun ProfileScreen() {

    Text(text = "profile", fontSize = 32.sp)

}

@Composable
private fun ReportsScreen() {

    Text(text = "Report", fontSize = 32.sp)

}

@Composable
private fun ReminderScreen() {

    Text(text = "reminder", fontSize = 32.sp)
}