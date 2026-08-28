package info.alihabibi.channels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object ChannelGraphRoute

@Serializable
object ChannelsList

@Serializable
object EditChannel

@Serializable
object AddNewChannel

fun NavGraphBuilder.channelsGraph(navController: NavController) {

    navigation<ChannelGraphRoute>(startDestination = ChannelsList) {

        composable<ChannelsList> {
            Text("Channels List", Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }

        composable<EditChannel> {

        }

        composable<AddNewChannel> {
            Text("Add New Channel", Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }

    }

}