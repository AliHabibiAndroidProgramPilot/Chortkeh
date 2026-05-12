package info.alihabibi.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import info.alihabibi.common_android.RequestNotificationPermission
import info.alihabibi.common_android.RequestSMSPermission
import info.alihabibi.ui.headrs.HomePageHeader
import info.alihabibi.ui.navigation.AppBottomNavigation
import info.alihabibi.ui.navigation.BottomNavItem
import info.alihabibi.ui.scaffolds.BaseScaffold
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeDestination(
    viewModel: HomeViewModel = koinViewModel(),
    onAnnouncements: () -> Unit = {}
) {

    var selectedBottomNavItem by remember { mutableStateOf(BottomNavItem.HOME.name) }
    val navItems = BottomNavItem.entries.toList()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeUiIntent.FetchSmsPermissionModalShownState)
    }

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
                BottomNavItem.HOME.name -> HomeScreen(
                    uiState = uiState,
                    onSmsModalShowed = {
                        viewModel.onEvent(HomeUiIntent.SaveSmsPermissionModalShownState(value = true))
                    },
                    onAnnouncements = onAnnouncements
                )

                BottomNavItem.PROFILE.name -> ProfileScreen()
                BottomNavItem.REPORTS.name -> ReportsScreen()
                BottomNavItem.REMINDER.name -> ReminderScreen()
            }

        }

    }

}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSmsModalShowed: () -> Unit = {},
    onAnnouncements: () -> Unit
) {

    val notificationPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val smsPermissionState = rememberPermissionState(permission = Manifest.permission.RECEIVE_SMS)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notificationPermissionState.status.isGranted)
        RequestNotificationPermission()
    if (uiState.isSmsModalShown == false && !smsPermissionState.status.isGranted) {
        RequestSMSPermission(onSmsModalShown = onSmsModalShowed)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HomePageHeader(
            isBadgeAvailable = false,
            onNavigationClick = onAnnouncements
        )

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