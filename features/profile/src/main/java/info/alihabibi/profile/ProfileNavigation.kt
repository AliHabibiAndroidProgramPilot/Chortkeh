package info.alihabibi.profile

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.profile.screens.PrivacyAndPolicyDestination
import info.alihabibi.profile.screens.ProfileDestination
import info.alihabibi.profile.screens.UserAccountInfoDestination
import info.alihabibi.profile.viewmodel.ProfileViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

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

       composable<Profile> { backStackEntry ->
           val parentEntry = remember(backStackEntry) {
               navController.getBackStackEntry<ProfileGraphRoute>()
           }
           val viewModel: ProfileViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
           ProfileDestination(
               viewModel = viewModel,
               onPrivacyAndPolicy = {
                   navController.navigate(PrivacyAndPolicy)
               },
               onUserAccountInfo = {
                   navController.navigate(UserAccountInfo)
               }
           )
       }

       composable<UserAccountInfo> { backStackEntry ->
           val parentEntry = remember(backStackEntry) {
               navController.getBackStackEntry<ProfileGraphRoute>()
           }
           val viewModel: ProfileViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
           UserAccountInfoDestination(
               viewModel = viewModel,
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