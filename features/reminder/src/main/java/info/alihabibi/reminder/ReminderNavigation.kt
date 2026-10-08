package info.alihabibi.reminder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alihabibi.reminder.screens.AddOrEditReminderDestination
import info.alihabibi.reminder.screens.RemindersListDestination
import kotlinx.serialization.Serializable

@Serializable
object ReminderGraphRoute

@Serializable
object RemindersList

@Serializable
data class AddOrEditReminder(val editingReminderId: Long? = null)

fun NavGraphBuilder.reminderGraph(navController: NavController) {

    navigation<ReminderGraphRoute>(startDestination = RemindersList) {

        composable<RemindersList> {
            RemindersListDestination(
                onNewReminder = {
                    navController.navigate(AddOrEditReminder())
                },
                onEditReminder = { reminderId ->
                    navController.navigate(AddOrEditReminder(reminderId))
                }
            )
        }

        composable<AddOrEditReminder> { backStackEntry ->
            val args = backStackEntry.toRoute<AddOrEditReminder>()
            AddOrEditReminderDestination(
                onBackPressed = {
                    navController.navigateUp()
                },
                editingReminderId = args.editingReminderId
            )
        }

    }

}