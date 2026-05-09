package info.alihabibi.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import info.alihabibi.ui.headrs.HomePageHeader
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
    ) { _ ->

        AnimatedContent(
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HomePageHeader(isBadgeAvailable = false)

    }

}

@Composable
private fun ProfileScreen() {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "profile", fontSize = 32.sp)

}

@Composable
private fun ReportsScreen() {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "Report", fontSize = 32.sp)

}

@Composable
private fun ReminderScreen() {

    //TODO(give window insets padding from Modifier for content)
    Text(text = "reminder", fontSize = 32.sp)

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeDestinationPreview() {

    HomeDestination()

}