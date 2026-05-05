package info.alihabibi.chortkeh.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import info.alihabibi.chortkeh.DemoNavHost
import info.alihabibi.chortkeh.Home
import info.alihabibi.chortkeh.OnBoarding
import info.alihabibi.designsystem.theme.ChortkehTheme
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
                when (val state = uiState.value) {

                    is MainActivityUiState.Loading -> { /* still showing splash screen */ }

                    is MainActivityUiState.Success -> {
                        val startDestination = when (state.isFirstLaunch) {
                            true -> OnBoarding
                            false -> Home
                        }

                        DemoNavHost(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }

                }
            }
        }
    }

}