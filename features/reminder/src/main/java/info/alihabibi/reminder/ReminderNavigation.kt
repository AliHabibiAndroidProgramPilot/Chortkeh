package info.alihabibi.reminder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.reminder.screens.AddOrEditReminderDestination
import info.alihabibi.reminder.screens.RemindersListDestination
import kotlinx.serialization.Serializable

@Serializable
object ReminderGraphRoute

@Serializable
object RemindersList

@Serializable
object AddOrEditReminder

fun NavGraphBuilder.reminderGraph(navController: NavController) {

    navigation<ReminderGraphRoute>(startDestination = RemindersList) {

        composable<RemindersList> {
            RemindersListDestination(
                onNewReminder = {
                    navController.navigate(AddOrEditReminder)
                }
            )
        }

        composable<AddOrEditReminder> {
            AddOrEditReminderDestination(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

    }

}