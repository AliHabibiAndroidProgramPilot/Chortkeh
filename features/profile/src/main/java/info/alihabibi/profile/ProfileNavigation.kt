package info.alihabibi.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.profile.screens.PrivacyAndPolicyDestination
import info.alihabibi.profile.screens.ProfileDestination
import info.alihabibi.profile.screens.UserAccountInfoDestination
import kotlinx.serialization.Serializable

@Serializable
object ProfileGraphRoute

@Serializable
object Profile

@Serializable
object PrivacyAndPolicy

@Serializable
object UserAccountInfo


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