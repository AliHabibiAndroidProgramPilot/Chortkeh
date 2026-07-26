package info.alihabibi.chortkeh.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import info.alihabibi.chortkeh.navigation.BottomNavItems
import info.alihabibi.chortkeh.navigation.DemoNavHost
import info.alihabibi.chortkeh.navigation.Home
import info.alihabibi.chortkeh.navigation.OnBoarding
import info.alihabibi.chortkeh.navigation.topLevelDestinations
import info.alihabibi.common_android.ObserveAsEvents
import info.alihabibi.common_android.snackbar.SnackBarController
import info.alihabibi.designsystem.theme.ChortkehTheme
import info.alihabibi.new_transaction.NewTransaction
import info.alihabibi.ui.navigation.AppBottomNavigation
import info.alihabibi.ui.scaffolds.BaseScaffold
import info.alihabibi.ui.snackbars.AppSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen() }

        setContent {
            ChortkehTheme {
                val navController = rememberNavController()
                val uiState = viewModel.uiState.collectAsState()

                val currentDestination by navController.currentBackStackEntryAsState()
                val shouldShowBottomBar = topLevelDestinations.any {
                    currentDestination?.destination?.hasRoute(it::class) == true
                }

                val snackBarHostState = remember { SnackbarHostState() }
                ObserveAsEvents(SnackBarController.event, snackBarHostState) { event ->
                    snackBarHostState.currentSnackbarData?.dismiss()
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionName,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed)
                        event.action?.invoke()
                }

                when (val state = uiState.value) {

                    is MainActivityUiState.Loading -> { /* still showing splash screen */ }

                    is MainActivityUiState.Success -> {
                        val startDestination = when (state.isFirstLaunch) {
                            true -> OnBoarding
                            false -> Home
                        }

                        BaseScaffold(
                            snackBarHost = {
                                SnackbarHost(
                                    modifier = Modifier.padding(bottom = 22.dp),
                                    hostState = snackBarHostState
                                ) { data ->
                                    AppSnackBar(
                                        description = data.visuals.message,
                                        isUndoAvailable = data.visuals.actionLabel != null,
                                        onUndo = { data.performAction() }
                                    )
                                }
                            },
                            bottomBar = {
                                if (shouldShowBottomBar)
                                    AppBottomNavigation(
                                        currentDestination = currentDestination?.destination,
                                        items = BottomNavItems.entries.map { it.toUiData() },
                                        onFabClick = {
                                            navController.navigate(NewTransaction)
                                        },
                                        onNavItemClicked = { navItem ->
                                            navController.navigate(navItem.route) {
                                                popUpTo(Home) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                            }
                        ) { innerPadding ->
                            DemoNavHost(
                                navController = navController,
                                modifier = Modifier.padding(paddingValues = innerPadding),
                                startDestination = startDestination
                            )
                        }
                    }
                }
            }
        }
    }

}