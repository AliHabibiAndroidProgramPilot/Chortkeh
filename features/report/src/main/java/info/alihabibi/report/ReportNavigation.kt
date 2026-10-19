package info.alihabibi.report

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.report.screen.AllTransactionsDestination
import info.alihabibi.report.screen.ReportDestination
import kotlinx.serialization.Serializable

@Serializable
object ReportGraphRoute

@Serializable
object Report

@Serializable
object AllTransactions

fun NavGraphBuilder.reportGraph(
    navController: NavController,
    onEditTransaction: (transactionId: Long) -> Unit = {}
) {

    navigation<ReportGraphRoute>(startDestination = Report) {

        composable<Report> {
            ReportDestination(
                onTransactionsList = {
                    navController.navigate(AllTransactions)
                }
            )
        }

        composable<AllTransactions> {
            AllTransactionsDestination(
                onEditTransaction = onEditTransaction,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }

    }

}