package info.alihabibi.report.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.chart_data.IncomeExpenseBucket
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.mapper.toUiOption
import info.alihabibi.model.ui_model.transaction.BarChartTimingOptionUiModel
import info.alihabibi.model.ui_model.transaction.CategoryTransactionExpensesUiModel
import info.alihabibi.model.ui_model.transaction.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class ReportViewModel(
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {

    val uiState: StateFlow<ReportUiState>
        field = MutableStateFlow(ReportUiState())

    init {
        uiState
            .map { it.reportTimingOption }
            .distinctUntilChanged()
            .flatMapLatest { timingOption ->
                transactionUseCases.getIncomeExpenseChartUseCase.invoke(timingOption.toDomain())
                    .map { buckets -> timingOption to buckets }
            }
            .onEach { (timingOption, bucket) ->
                val bucketUiOption = bucket.map(IncomeExpenseBucket::toUiModel)
                uiState.update {
                    it.copy(
                        chartTimingOption = timingOption,
                        chartIncome = bucketUiOption.map { bucketData -> bucketData.incomeAmount },
                        chartExpense = bucketUiOption.map { bucketData -> bucketData.expenseAmount }
                    )
                }
            }
            .launchIn(viewModelScope)

        combine(
            transactionUseCases.hasTransactionUseCases.invoke(),
            transactionUseCases.hasOutcomeTransactionUseCase.invoke(),
            transactionUseCases.getLastTransactions.invoke(5, null)
        ) { hasTransaction, hasOutcomeTransaction, last5transactions ->
            uiState.update {
                it.copy(
                    hasTransaction = hasTransaction,
                    hasOutcomeTransaction = hasOutcomeTransaction,
                    lastTransactions = last5transactions
                        .map { transaction -> transaction.toUiModel() }
                        .take(5)
                )
            }
        }.launchIn(viewModelScope)

        uiState
            .map { Pair(it.reportTimingOption, it.pieChartTransactionType) }
            .distinctUntilChanged()
            .flatMapLatest { (timingOption, transactionType) ->
                transactionUseCases.getCategoryExpensesUseCase.invoke(
                    timing = timingOption.toDomain(),
                    type = transactionType.toDomain()
                )
            }
            .onEach { categoryTransactionExpenses ->
                val categoryTransactionExpensesUiModel = categoryTransactionExpenses
                    .filter { it.totalAmount > 0 }
                    .map(CategoryTransactionExpenses::toUiOption)
                uiState.update {
                    it.copy(
                        expensesByCategories = categoryTransactionExpensesUiModel
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: ReportUiIntent) {
        when (event) {

            is ReportUiIntent.ReportTimingOptionChanged -> changeReportTimingOption(event.timingOption)

            is ReportUiIntent.PieChartTransactionTypeChanged -> changePieChartTransactionType(event.type)

        }
    }

    private fun changeReportTimingOption(timingOption: BarChartTimingOptionUiModel) {
        uiState.update { it.copy(reportTimingOption = timingOption) }
    }

    private fun changePieChartTransactionType(type: TransactionTypeOptionUiModel) {
        uiState.update { it.copy(pieChartTransactionType = type) }
    }

}

sealed interface ReportUiIntent {

    data class ReportTimingOptionChanged(val timingOption: BarChartTimingOptionUiModel) : ReportUiIntent

    data class PieChartTransactionTypeChanged(val type: TransactionTypeOptionUiModel) : ReportUiIntent

}

// todo -> both `reportTimingOption` and `chartTimingOption` reference the same data, we distinguished it for chart correspond and stop glitching, fix it later
@Immutable
data class ReportUiState(
    val hasTransaction: Boolean = false,
    val hasOutcomeTransaction: Boolean = false,
    // describes user's latest selection -> works with toggle
    val reportTimingOption: BarChartTimingOptionUiModel = BarChartTimingOptionUiModel.WEEKLY,
    // describes the chart data, not the user's latest selection
    val chartTimingOption: BarChartTimingOptionUiModel = BarChartTimingOptionUiModel.WEEKLY,
    val pieChartTransactionType: TransactionTypeOptionUiModel = TransactionTypeOptionUiModel.OUTCOME,
    val chartIncome: List<Long> = emptyList(),
    val chartExpense: List<Long> = emptyList(),
    val expensesByCategories: List<CategoryTransactionExpensesUiModel> = emptyList(),
    val lastTransactions: List<TransactionUiModel> = emptyList()
)