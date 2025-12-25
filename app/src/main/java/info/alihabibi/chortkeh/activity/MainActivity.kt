package info.alihabibi.chortkeh.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import info.alihabibi.chortkeh.DemoNavHost
import info.alihabibi.chortkeh.Home
import info.alihabibi.chortkeh.OnBoarding
import info.alihabibi.designsystem.theme.ChortkehTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChortkehTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                var startDestinationState by remember { mutableStateOf<Any?>(null) }
                if (uiState.isFirstLaunch != null) {
                    when (uiState.isFirstLaunch!!) {
                        true -> {
                            startDestinationState = OnBoarding
                            viewModel.onEvent(MainActivityIntent.ChangeFirstLaunchFlag(false))
                        }
                        false -> startDestinationState = Home
                    }
                    val navController = rememberNavController()
                    DemoNavHost(
                        navController = navController,
//                        startDestination = OnBoarding
                        startDestination = startDestinationState!!
                    )
                }
            }
        }
    }

}