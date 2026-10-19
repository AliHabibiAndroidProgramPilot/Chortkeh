package info.alihabibi.report

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import info.alihabibi.domain.models.transaction.chart_data.IncomeExpenseBucket
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.transaction.BarChartTimingOptionUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

        transactionUseCases.hasTransactionUseCases.invoke().onEach { hasTransaction ->
            uiState.update { it.copy(hasTransaction = hasTransaction) }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ReportUiIntent) {
        when (event) {

            is ReportUiIntent.ReportTimingOptionChanged -> changeReportTimingOption(event.timingOption)

        }
    }

    private fun changeReportTimingOption(timingOption: BarChartTimingOptionUiModel) {
        uiState.update { it.copy(reportTimingOption = timingOption) }
    }

}

sealed interface ReportUiIntent {

    data class ReportTimingOptionChanged(val timingOption: BarChartTimingOptionUiModel) : ReportUiIntent

}

@Immutable
data class ReportUiState(
    val hasTransaction: Boolean = false,
    // describes user's latest selection -> works with toggle
    val reportTimingOption: BarChartTimingOptionUiModel = BarChartTimingOptionUiModel.WEEKLY,
    // describes the chart data, not the user's latest selection
    val chartTimingOption: BarChartTimingOptionUiModel = BarChartTimingOptionUiModel.WEEKLY,
    val chartIncome: List<Long> = emptyList(),
    val chartExpense: List<Long> = emptyList()
)