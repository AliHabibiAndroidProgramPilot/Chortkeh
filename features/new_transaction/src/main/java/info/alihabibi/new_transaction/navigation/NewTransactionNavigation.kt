package info.alihabibi.new_transaction.navigation

import android.content.Intent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import info.alihabibi.domain.Keys
import info.alihabibi.model.data.TransactionData
import info.alihabibi.model.mapper.toUiPrefill
import info.alihabibi.new_transaction.NewTransactionUiIntent
import info.alihabibi.new_transaction.NewTransactionViewModel
import info.alihabibi.new_transaction.screens.AddCategoryDestination
import info.alihabibi.new_transaction.screens.NewTransactionDestination
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

const val NEW_TRANSACTION_DEEP_LINK_BASE_URI = "chortkeh://new_transaction"

@Serializable
data class NewTransactionGraphRoute(val editingTransactionId: Long? = null)

@Serializable
object NewTransaction

@Serializable
data class AddCategory(val editingCategoryId: Int? = null)

fun NavGraphBuilder.newTransactionGraph(
    navController: NavController,
    onAddNewChannel: () -> Unit = {}
) {

    navigation<NewTransactionGraphRoute>(startDestination = NewTransaction) {

        composable<NewTransaction>(
            deepLinks = listOf(
                navDeepLink<NewTransactionGraphRoute>(basePath = NEW_TRANSACTION_DEEP_LINK_BASE_URI),
                navDeepLink { uriPattern = NEW_TRANSACTION_DEEP_LINK_BASE_URI }
            )
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<NewTransactionGraphRoute>()
            }
            val viewModel: NewTransactionViewModel = koinViewModel(viewModelStoreOwner = parentEntry)
            val editingTransactionId = remember(parentEntry) {
                try {
                    parentEntry.toRoute<NewTransactionGraphRoute>().editingTransactionId
                } catch (_: Exception) {
                    null
                }
            }

            val intentExtra = backStackEntry.arguments?.let {
                BundleCompat.getParcelable(it, NavController.KEY_DEEP_LINK_INTENT, Intent::class.java)
            }
            val prefill = remember(intentExtra) {
                intentExtra
                    ?.let { IntentCompat.getParcelableExtra(it, Keys.EXTRA_ID_TRANSACTION_DATA, TransactionData::class.java) }
                    ?.toUiPrefill()
            }

            LaunchedEffect(prefill) {
                if (prefill != null)
                    viewModel.onEvent(
                        NewTransactionUiIntent.ApplyPrefill(
                            prefill,
                            intentExtra?.getIntExtra(Keys.EXTRA_NOTIFICATION_ID, -1) ?: -1
                        )
                    )
            }

            NewTransactionDestination(
                viewModel = viewModel,
                editingTransactionId = editingTransactionId,
                onAddNewCategory = {
                    navController.navigate(AddCategory())
                },
                onAddNewChannel = onAddNewChannel,
                onEditCategory = { categoryId ->
                    navController.navigate(AddCategory(categoryId))
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