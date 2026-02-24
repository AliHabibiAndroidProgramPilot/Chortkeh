package info.alihabibi.home

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import info.alihabibi.designsystem.R
import info.alihabibi.ui.headrs.HomePageHeader
import info.alihabibi.ui.navigation.AppBottomNavigation
import info.alihabibi.ui.navigation.BottomNavItem
import info.alihabibi.ui.scaffolds.BaseScaffold

@Composable
fun HomeDestination() {

    HomeScreen()

}

@Composable
private fun HomeScreen() {

    BaseScaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = { HomePageHeader(isBadgeAvailable = false) },
        bottomBar = {
            AppBottomNavigation(
                items = listOf(
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.profile),
                        icon = painterResource(id = R.drawable.profile)
                    ),
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.reminder),
                        icon = painterResource(id = R.drawable.reminder)
                    ),
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.reports),
                        icon = painterResource(id = R.drawable.reports)
                    ),
                    BottomNavItem(
                        route = null,
                        label = stringResource(id = R.string.home),
                        icon = painterResource(id = R.drawable.home)
                    )
                ),
                onItemClicked = {},
                onFabClicked = {}
            )
        }
    ) { }

}