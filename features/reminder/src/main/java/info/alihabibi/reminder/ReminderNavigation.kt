package info.alihabibi.reminder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alihabibi.reminder.screens.AddOrEditReminderDestination
import info.alihabibi.reminder.screens.RemindersListDestination
import kotlinx.serialization.Serializable

const val REMINDER_DEEP_LINK_BASE_URI = "chortkeh://reminder"

@Serializable
object ReminderGraphRoute

@Serializable
object RemindersList

@Serializable
data class AddOrEditReminder(val editingReminderId: Long? = null)

fun NavGraphBuilder.reminderGraph(navController: NavController) {

    navigation<ReminderGraphRoute>(startDestination = RemindersList) {

        composable<RemindersList>(
            deepLinks = listOf(
                navDeepLink<AddOrEditReminder>(basePath = REMINDER_DEEP_LINK_BASE_URI),
                navDeepLink { uriPattern = "$REMINDER_DEEP_LINK_BASE_URI/{editingReminderId}" }
            )
        ) {
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