package info.alihabibi.report.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.common_android.AndroidUtils
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Gray7
import info.alihabibi.designsystem.theme.NeutralGray
import info.alihabibi.model.ui_model.transaction.BarChartTimingOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import info.alihabibi.report.viewmodels.ReportUiIntent
import info.alihabibi.report.viewmodels.ReportUiState
import info.alihabibi.report.viewmodels.ReportViewModel
import info.alihabibi.ui.buttons.AppToggle
import info.alihabibi.ui.charts.IncomeExpenseBarChart
import info.alihabibi.ui.charts.pie_chart.AppPieChart
import info.alihabibi.ui.charts.pie_chart.PieChartData
import info.alihabibi.ui.headrs.AppHeader
import info.alihabibi.ui.items.TransactionItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReportDestination(
    viewModel: ReportViewModel = koinViewModel(),
    onTransactionsList: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportScreen(
        uiState = uiState,
        onReportTimingOptionChanged = { timingOption ->
            viewModel.onEvent(ReportUiIntent.ReportTimingOptionChanged(timingOption))
        },
        onPieChartTransactionTypeChanged = { transactionType ->
            viewModel.onEvent(ReportUiIntent.PieChartTransactionTypeChanged(transactionType))
        },
        onTransactionsList = onTransactionsList
    )

}

@Composable
private fun ReportScreen(
    uiState: ReportUiState,
    onTransactionsList: () -> Unit = {},
    onReportTimingOptionChanged: (timingOption: BarChartTimingOptionUiModel) -> Unit = {},
    onPieChartTransactionTypeChanged: (type: TransactionTypeOptionUiModel) -> Unit = {}
) {

    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    val isCompact = !windowAdaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(600)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 1000.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppHeader(
                title = stringResource(id = R.string.reports),
                isActionAvailable = false,
                isNavigationAvailable = false
            )

            if (!uiState.hasTransaction)
                EmptyReportState(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    isCompact = isCompact
                )
            else {
                if (isCompact) {
                    ReportContentCompact(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(state = rememberScrollState()),
                        uiState = uiState,
                        onReportTimingOptionChanged = onReportTimingOptionChanged,
                        onPieChartTransactionTypeChanged = onPieChartTransactionTypeChanged,
                        onTransactionsList = onTransactionsList
                    )
                } else {
                    ReportContentWide(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(state = rememberScrollState()),
                        uiState = uiState,
                        onReportTimingOptionChanged = onReportTimingOptionChanged,
                        onPieChartTransactionTypeChanged = onPieChartTransactionTypeChanged,
                        onTransactionsList = onTransactionsList
                    )
                }
            }

        }

    }

}

@Composable
private fun ReportContentCompact(
    modifier: Modifier = Modifier,
    uiState: ReportUiState,
    onTransactionsList: () -> Unit = {},
    onReportTimingOptionChanged: (timingOption: BarChartTimingOptionUiModel) -> Unit = {},
    onPieChartTransactionTypeChanged: (type: TransactionTypeOptionUiModel) -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppToggle(
            toggleItems = BarChartTimingOptionUiModel.entries.toList(),
            itemTitle = { timingOption -> stringResource(id = timingOption.labelRes) },
            selectedOption = uiState.reportTimingOption,
            onToggleSelectionChanged = onReportTimingOptionChanged
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        IncomeExpenseBarChart(
            labels = dynamicBarChartLabels(uiState.chartTimingOption),
            incomes = uiState.chartIncome,
            expenses = uiState.chartExpense
        )

        Spacer(modifier = Modifier.height(height = 36.dp))

        AppToggle(
            toggleItems = TransactionTypeOptionUiModel.entries.toList(),
            itemTitle = { transactionType -> stringResource(id = transactionType.labelRes) },
            selectedOption = uiState.pieChartTransactionType,
            onToggleSelectionChanged = onPieChartTransactionTypeChanged
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        PieChartSection(uiState = uiState)

        Spacer(modifier = Modifier.height(height = 16.dp))

        LastTransactionsSection(
            lastTransactions = uiState.lastTransactions,
            hasTransaction = uiState.hasTransaction,
            onTransactionsList = onTransactionsList
        )

    }

}

@Composable
private fun ReportContentWide(
    modifier: Modifier = Modifier,
    uiState: ReportUiState,
    onTransactionsList: () -> Unit = {},
    onReportTimingOptionChanged: (timingOption: BarChartTimingOptionUiModel) -> Unit = {},
    onPieChartTransactionTypeChanged: (type: TransactionTypeOptionUiModel) -> Unit = {}
) {

    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Left Column: Income & Expense Bar Chart
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppToggle(
                toggleItems = BarChartTimingOptionUiModel.entries.toList(),
                itemTitle = { timingOption -> stringResource(id = timingOption.labelRes) },
                selectedOption = uiState.reportTimingOption,
                onToggleSelectionChanged = onReportTimingOptionChanged
            )

            Spacer(modifier = Modifier.height(16.dp))

            IncomeExpenseBarChart(
                labels = dynamicBarChartLabels(uiState.chartTimingOption),
                incomes = uiState.chartIncome,
                expenses = uiState.chartExpense
            )

        }

        // Right Column: Pie Chart Breakdown & Recent Transactions
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppToggle(
                toggleItems = TransactionTypeOptionUiModel.entries.toList(),
                itemTitle = { transactionType -> stringResource(id = transactionType.labelRes) },
                selectedOption = uiState.pieChartTransactionType,
                onToggleSelectionChanged = onPieChartTransactionTypeChanged
            )

            Spacer(modifier = Modifier.height(16.dp))

            PieChartSection(uiState = uiState)

            Spacer(modifier = Modifier.height(16.dp))

            LastTransactionsSection(
                lastTransactions = uiState.lastTransactions,
                hasTransaction = uiState.hasTransaction,
                onTransactionsList = onTransactionsList
            )

        }

    }

}

@Composable
private fun LastTransactionsSection(
    lastTransactions: List<TransactionUiModel>,
    hasTransaction: Boolean,
    onTransactionsList: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        repeat(lastTransactions.size) { index ->
            TransactionItem(
                title = lastTransactions[index].category?.title.orEmpty(),
                transactionAmount = lastTransactions[index].amount,
                subTitle = PersianDateFormatter.format(
                    lastTransactions[index].year,
                    lastTransactions[index].month,
                    lastTransactions[index].dayOfWeekName,
                    lastTransactions[index].day,
                    lastTransactions[index].time
                ),
                iconResId = lastTransactions[index].category?.icon?.iconResId ?: R.drawable.category_ic_others
            )
        }

        if (hasTransaction)
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.7f)
                    .padding(vertical = 8.dp),
                onClick = onTransactionsList,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.all_transactions),
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                )

            }

    }

}

@Composable
private fun PieChartSection(uiState: ReportUiState) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(height = 8.dp))

            if (uiState.expensesByCategories.isEmpty())
                Image(
                    modifier = Modifier
                        .size(width = 154.dp, height = 184.dp)
                        .padding(top = 24.dp, bottom = 40.dp),
                    painter = painterResource(id = R.drawable.empty_transaction),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            else
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(percent = 8),
                    border = BorderStroke(width = 1.5.dp, color = NeutralGray),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {

                    val data = remember(key1 = uiState.expensesByCategories) {
                        val colorsList = AndroidUtils.generateDistinctColors(uiState.expensesByCategories.size)
                        uiState.expensesByCategories.fastMapIndexed { index, category ->
                            PieChartData(
                                category.categoryTitle,
                                category.totalAmount,
                                colorsList[index]
                            )
                        }
                    }

                    AppPieChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        segments = data
                    )

                }

        }

    }

}

@Composable
private fun EmptyReportState(
    modifier: Modifier = Modifier,
    isCompact: Boolean = true
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.empty_report),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(width = 180.dp, height = 180.dp)
            )

            Text(
                text = stringResource(id = R.string.empty_balance_state),
                style = MaterialTheme.typography.labelLarge.copy(color = Gray7)
            )

        }

        if (isCompact)
            Image(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 68.dp, bottom = 16.dp)
                    .rotate(degrees = -25f),
                painter = painterResource(id = R.drawable.arrow_shape_down),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

    }

}

@Composable
fun dynamicBarChartLabels(timing: BarChartTimingOptionUiModel): List<String> = when (timing) {
    BarChartTimingOptionUiModel.YEARLY -> PersianDateFormatter.persianMonths
    BarChartTimingOptionUiModel.MONTHLY -> (1..5).map { stringResource(id = R.string.week_of_month, it) }
    BarChartTimingOptionUiModel.WEEKLY -> PersianDateFormatter.persianWeekDays
}