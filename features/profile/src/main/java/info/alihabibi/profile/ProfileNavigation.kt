package info.alihabibi.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.profile.screens.PrivacyAndPolicyDestination
import info.alihabibi.profile.screens.ProfileDestination
import info.alihabibi.profile.screens.UserAccountInfoDestination
import kotlinx.serialization.Serializable

@Serializable
object Profile

@Serializable
object PrivacyAndPolicy

@Serializable
object UserAccountInfo

@Composable
fun NavGraphBuilder.profileGraph(navController: NavController) {

   navigation<ProfileGraphRoute>(startDestination = Profile) {

       composable<Profile> {
           ProfileDestination()
       }

       composable<UserAccountInfo> {
           UserAccountInfoDestination(
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

   }

}