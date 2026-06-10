package info.alihabibi.chortkeh

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.announcements.AnnouncementsDestination
import info.alihabibi.home.HomeDestination
import info.alihabibi.new_transaction.NewTransactionDestination
import info.alihabibi.onboarding.OnBoardingDestination
import info.alihabibi.otp.OtpDestination
import info.alihabibi.privacy_and_policy.PrivacyAndPolicyDestination
import info.alihabibi.user_account_info.UserAccountInfoDestination
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
object PrivacyAndPolicy

@Serializable
object UserAccountInfo

@Serializable
object NewTransaction

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

        composable<PrivacyAndPolicy> {
            PrivacyAndPolicyDestination(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<UserAccountInfo> {
            UserAccountInfoDestination(
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