package info.alihabibi.chortkeh

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.onboarding.OnBoardingDestination
import info.alihabibi.otp.OtpDestination
import kotlinx.serialization.Serializable

@Serializable
object OnBoarding

@Serializable
object Otp

@Serializable
object Home

@Composable
fun DemoNavHost(
    navController: NavHostController,
    startDestination: Any = OnBoarding
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<OnBoarding> {
            OnBoardingDestination(
                onEnterApplication = {
                    navController.navigate(Otp) {
                        popUpTo(OnBoarding) { inclusive = true }
                    }
                }
            )
        }

        composable<Otp> {
            OtpDestination()
        }

        composable<Home> {
            Text("HOME", fontSize = 48.sp)
        }

    }

}