package info.alihabibi.chortkeh.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import info.alihabibi.announcements.AnnouncementsDestination
import info.alihabibi.channels.AddChannel
import info.alihabibi.channels.ChannelGraphRoute
import info.alihabibi.channels.channelsGraph
import info.alihabibi.home.HomeDestination
import info.alihabibi.new_transaction.NewTransactionGraphRoute
import info.alihabibi.new_transaction.newTransactionGraph
import info.alihabibi.onboarding.OnBoardingDestination
import info.alihabibi.profile.Profile
import info.alihabibi.profile.profileGraph
import info.alihabibi.reminder.RemindersList
import info.alihabibi.reminder.reminderGraph
import info.alihabibi.report.Report
import info.alihabibi.report.reportGraph
import kotlinx.serialization.Serializable

@Serializable
object OnBoarding

@Serializable
object Home

@Serializable
object Announcements

val topLevelDestinations = setOfNotNull(Home, Profile, Report, RemindersList)

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
                    navController.navigate(ChannelGraphRoute)
                },
                onNewChannel = {
                    navController.navigate(ChannelGraphRoute)
                    navController.navigate(AddChannel)
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

        profileGraph(navController = navController)

        newTransactionGraph(
            navController = navController,
            onAddNewChannel = {
                navController.navigate(ChannelGraphRoute)
                navController.navigate(AddChannel)
            }
        )

        reportGraph(
            navController = navController,
            onEditTransaction = { transactionId ->
                navController.navigate(NewTransactionGraphRoute(transactionId))
            }
        )

        channelsGraph(navController = navController)

        reminderGraph(navController = navController)

    }

}