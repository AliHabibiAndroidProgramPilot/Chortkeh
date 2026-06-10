package info.alihabibi.chortkeh

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.announcements.AnnouncementsDestination
import info.alihabibi.home.HomeDestination
import info.alihabibi.new_transaction.NewTransactionDestination
import info.alihabibi.onboarding.OnBoardingDestination
import info.alihabibi.otp.OtpDestination
import kotlinx.serialization.Serializable

@Serializable
object OnBoarding

@Serializable
object Otp

@Serializable
object Home

@Serializable
object Announcements

@Serializable
object ProfileGraphRoute

@Serializable
object NewTransaction

@Composable
fun DemoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any = OnBoarding
) {

    NavHost(
        navController = navController,
        modifier = modifier,
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
            HomeDestination(
                onAnnouncements = {
                    navController.navigate(Announcements)
                },
                onProfile = {
                    navController.navigate(ProfileGraphRoute)
                }
            )
        }

        composable<Announcements> {
            AnnouncementsDestination(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<NewTransaction> {
            NewTransactionDestination(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

    }

}