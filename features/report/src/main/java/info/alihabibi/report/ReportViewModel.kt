package info.alihabibi.report

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.Utils.loog
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.ui_model.transaction.BarChartTimingOptionUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.forEach
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
            }
            .onEach {
                it.loog()
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
    val reportTimingOption: BarChartTimingOptionUiModel = BarChartTimingOptionUiModel.WEEKLY
)