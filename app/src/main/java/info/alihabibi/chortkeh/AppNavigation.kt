package info.alihabibi.chortkeh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.announcements.AnnouncementsDestination
import info.alihabibi.domain.local.keys.Keys
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

        composable<Home> { backStackEntry ->
            val userAccountInfoSaved by backStackEntry.savedStateHandle
                .getStateFlow(Keys.USER_SAVED_ACCOUNT_INFO, false)
                .collectAsStateWithLifecycle()
            HomeDestination(
                shouldShowSuccessfulDataSaved = userAccountInfoSaved,
                onUserInfoSavedConsumed = {
                    backStackEntry.savedStateHandle.remove<Boolean>(Keys.USER_SAVED_ACCOUNT_INFO)
                },
                onAnnouncements = {
                    navController.navigate(Announcements)
                },
                onExitOfAccount = {
                    navController.navigate(OnBoarding) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onPrivacyAndPolicy = {
                    navController.navigate(PrivacyAndPolicy)
                },
                onUserAccountInfo = {
                    navController.navigate(UserAccountInfo)
                },
                onNewTransaction = {
                    navController.navigate(NewTransaction)
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
                onBackPressed = { userSavedData ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(Keys.USER_SAVED_ACCOUNT_INFO, userSavedData)
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