package info.alihabibi.reminder

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.reminder.screens.AddOrEditReminderDestination
import info.alihabibi.reminder.screens.RemindersListDestination
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ReminderGraphRoute

@Serializable
object RemindersList

@Serializable
object AddOrEditReminder

fun NavGraphBuilder.reminderGraph(navController: NavController) {

    navigation<ReminderGraphRoute>(startDestination = RemindersList) {

        composable<RemindersList> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<ReminderGraphRoute>()
            }
            val viewModel: ReminderViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            RemindersListDestination(
                viewModel = viewModel,
                onNewReminder = {
                    navController.navigate(AddOrEditReminder)
                }
            )
        }

        composable<AddOrEditReminder> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<ReminderGraphRoute>()
            }
            val viewModel: ReminderViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            AddOrEditReminderDestination(
                viewModel = viewModel,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

    }

}