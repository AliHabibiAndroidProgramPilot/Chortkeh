package info.alihabibi.chortkeh

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.home.HomeDestination
import info.alihabibi.onboarding.OnBoardingDestination
import info.alihabibi.otp.OtpDestination
import info.alihabibi.ui.navigation.AppBottomNavigation
import info.alihabibi.ui.navigation.BottomNavItem
import info.alihabibi.ui.scaffolds.BaseScaffold
import kotlinx.serialization.Serializable

@Serializable
object OnBoarding

@Serializable
object Otp

@Serializable
object Home

@Serializable
object Profile

@Serializable
object Reminder

@Serializable
object Reports

@Composable
fun DemoNavHost(
    navController: NavHostController,
    startDestination: Any = OnBoarding
) {

    BaseScaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavigation(
                onNavItemClicked = { item ->
                    val destination = when(item) {
                        BottomNavItem.HOME -> Home
                        BottomNavItem.PROFILE -> Profile
                        BottomNavItem.REMINDER -> Reminder
                        BottomNavItem.REPORTS -> Reports
                    }
                    navController.navigate(destination)
                },
                onFabClick = {}
            )
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            composable<OnBoarding> {
                OnBoardingDestination(
                    onEnterApplication = {
                        navController.navigate(Home) {
                            popUpTo(OnBoarding) { inclusive = true }
                        }
                    }
                )
            }

            composable<Otp> {
                OtpDestination()
            }

            composable<Home> {
                HomeDestination()
            }

            composable<Profile> {
                Text(text = "profile", fontSize = 38.sp)
            }

            composable<Reminder> {
                Text(text = "Reminders", fontSize = 38.sp)
            }

            composable<Reports> {
                Text(text = "Reports", fontSize = 38.sp)
            }

        }

    }

}