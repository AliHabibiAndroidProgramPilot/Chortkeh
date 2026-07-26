package info.alihabibi.new_transaction

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alihabibi.new_transaction.screens.AddCategoryDestination
import info.alihabibi.new_transaction.screens.NewTransactionDestination
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object NewTransactionGraphRoute

@Serializable
object NewTransaction

@Serializable
data class AddCategory(val editingCategoryId: Int? = null)

fun NavGraphBuilder.newTransactionGraph(navController: NavController) {

    navigation<NewTransactionGraphRoute>(startDestination = NewTransaction) {

        composable<NewTransaction> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<NewTransactionGraphRoute>()
            }
            val viewModel: NewTransactionViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            NewTransactionDestination(
                viewModel = viewModel,
                onAddNewCategory = {
                    navController.navigate(AddCategory(editingCategoryId = null))
                },
                onEditCategory = { categoryId ->
                    navController.navigate(AddCategory(editingCategoryId = categoryId))
                },
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

        composable<AddCategory> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<NewTransactionGraphRoute>()
            }
            val viewModel: NewTransactionViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            val args = backStackEntry.toRoute<AddCategory>()
            AddCategoryDestination(
                viewModel = viewModel,
                editingCategoryId = args.editingCategoryId,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

    }

}