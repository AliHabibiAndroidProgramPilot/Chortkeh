package info.alihabibi.new_transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object NewTransactionGraphRoute

@Serializable
object NewTransaction

@Serializable
object AddCategory

fun NavGraphBuilder.newTransactionGraph(navController: NavController) {

    navigation<NewTransactionGraphRoute>(startDestination = NewTransaction) {

        composable<NewTransaction> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<NewTransactionGraphRoute>()
            }
            val viewModel: NewTransactionViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            NewTransactionDestination(
                viewModel = viewModel,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<AddCategory> {

        }

    }

}