package info.alihabibi.channels

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alihabibi.channels.screens.AddChannelDestination
import info.alihabibi.channels.screens.ChannelsListDestination
import info.alihabibi.channels.screens.EditChannelDestination
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ChannelGraphRoute

@Serializable
object ChannelsList

@Serializable
data class EditChannel(val editingChannelId: Int? = null)

@Serializable
object AddChannel

fun NavGraphBuilder.channelsGraph(navController: NavController) {

    navigation<ChannelGraphRoute>(startDestination = ChannelsList) {

        composable<ChannelsList> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<ChannelGraphRoute>()
            }
            val viewModel: ChannelsViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            ChannelsListDestination(
                viewModel = viewModel,
                onEditChannel = { channelId ->
                    navController.navigate(EditChannel(channelId))
                },
                onAddNewChannel = {
                    navController.navigate(AddChannel)
                },
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<EditChannel> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<ChannelGraphRoute>()
            }
            val viewModel: ChannelsViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            val args = backStackEntry.toRoute<EditChannel>()
            EditChannelDestination(
                viewModel = viewModel,
                editingChannelId = args.editingChannelId,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<AddChannel> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<ChannelGraphRoute>()
            }
            val viewModel: ChannelsViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            AddChannelDestination(
                viewModel = viewModel,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

    }

}