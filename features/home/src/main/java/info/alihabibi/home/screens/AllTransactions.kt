package info.alihabibi.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.ErrorRed
import info.alihabibi.designsystem.theme.GreenSuccessLight
import info.alihabibi.home.AllTransactionsViewModel
import info.alihabibi.model.ui_model.transaction.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.items.TransactionItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllTransactionsDestination(
    viewModel: AllTransactionsViewModel = koinViewModel(),
    onEditTransaction: (id: Long) -> Unit = {},
    onBackPressed: () -> Unit
) {

    val transactions by viewModel.transactions.collectAsStateWithLifecycle()

    AllTransactionsScreen(
        transactions = transactions,
        onEditTransaction = onEditTransaction,
        onBackPressed = onBackPressed
    )

}

@Composable
private fun AllTransactionsScreen(
    transactions: List<TransactionUiModel>,
    onEditTransaction: (id: Long) -> Unit = {},
    onBackPressed: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                title = stringResource(id = R.string.all_transactions),
                isActionAvailable = false,
                onNavigationClicked = onBackPressed
            )

            LazyColumn(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {

                items(
                    items = transactions,
                    key = { it.id }
                ) { transaction ->

                    TransactionItem(
                        title = transaction.category?.title.orEmpty(),
                        transactionAmount = transaction.amount,
                        subTitle = PersianDateFormatter.format(
                            transaction.year,
                            transaction.month,
                            transaction.dayOfWeekName,
                            transaction.day,
                            transaction.time
                        ),
                        iconResId = transaction.category?.icon?.iconResId ?: R.drawable.category_ic_others,
                        needsTypeTag = true,
                        tag = stringResource(id = transaction.type.labelRes),
                        tagColor = if (transaction.type == TransactionTypeOptionUiModel.INCOME)
                            GreenSuccessLight.copy(alpha = 0.25f)
                        else
                            ErrorRed.copy(alpha = 0.25f),
                        clickable = true,
                        onClick = {
                            onEditTransaction(transaction.id)
                        }
                    )

                }

            }

        }

    }

}