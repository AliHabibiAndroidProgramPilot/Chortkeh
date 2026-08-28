package info.alihabibi.chortkeh.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.announcements.AnnouncementsDestination
import info.alihabibi.home.HomeDestination
import info.alihabibi.new_transaction.newTransactionGraph
import info.alihabibi.onboarding.OnBoardingDestination
import info.alihabibi.profile.Profile
import info.alihabibi.profile.profileGraph
import kotlinx.serialization.Serializable

@Serializable
object OnBoarding

@Serializable
object Home

@Serializable
object Announcements

/** non usable here, should be in its own module with a sub graph here! currently using it as help for Bottom nav bar implementation */
@Serializable
object Report

/** non usable here, should be in its own module with a sub graph here! currently using it as help for Bottom nav bar implementation */
@Serializable
object Reminder

val topLevelDestinations = setOfNotNull(Home, Profile, Report, Reminder)

@Composable
fun DemoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any = OnBoarding
) {

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination,
        enterTransition = { if (isBottomNavTransition()) bottomNavEnter() else pushEnter() },
        exitTransition = { if (isBottomNavTransition()) bottomNavExit() else pushExit() },
        popEnterTransition = { popEnter() },
        popExitTransition = { popExit() }
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

        composable<Home> {
            HomeDestination(
                onAnnouncements = {
                    navController.navigate(Announcements)
                },
                onChannels = {

                },
                onNewChannel = {

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

        composable<Report> {
            Text("REPORTS")
        }

        composable<Reminder> {
            Text("REminder")
        }

        profileGraph(navController = navController)

        newTransactionGraph(navController = navController)

    }

}