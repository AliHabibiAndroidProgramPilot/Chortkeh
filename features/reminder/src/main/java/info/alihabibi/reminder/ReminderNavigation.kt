package info.alihabibi.reminder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object ReminderGraphRoute

@Serializable
object RemindersList

fun NavGraphBuilder.reminderGraph(navController: NavController) {

    navigation<ReminderGraphRoute>(startDestination = RemindersList) {

        composable<RemindersList> {

        }

    }

}