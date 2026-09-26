package info.alihabibi.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.Utils
import info.alihabibi.common.Utils.loog
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.mapper.toUiOption
import info.alihabibi.model.ui_model.channel.ChannelUiModel
import info.alihabibi.model.ui_model.transaction.CategoryTransactionExpensesUiModel
import info.alihabibi.model.ui_model.transaction.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import info.alihabibi.ui.charts.GaugeChartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataStoreUseCases: DatastoreUseCases,
    transactionUseCases: TransactionUseCases,
    channelsUseCase: ChannelUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        combine(
            dataStoreUseCases.getIsSmsModalShownUseCase.invoke(),
            channelsUseCase.getChannelsUseCase.invoke(),
            channelsUseCase.getTotalBalanceUseCase.invoke(),
            transactionUseCases.getLastTransactions.invoke(5, TransactionTypeOptionUiModel.OUTCOME.name)
        ) { isSmsModalShown, channels, totalBalance, lastTransactions ->
            val uiChannels = channels.map {
                if (it.isAppDefaultChannel)
                    it.copy(channelBalance = totalBalance).toUiModel()
                else
                    it.toUiModel()
            }
            val uiLastTransactions = lastTransactions.fastMap(Transaction::toUiModel)
            _uiState.update {
                it.copy(
                    isSmsModalShown = isSmsModalShown,
                    formattedTotalBalance = Utils.decimalFormatterPattern.format(totalBalance),
                    lastTransactions = uiLastTransactions,
                    channels = uiChannels
                )
            }
        }.launchIn(viewModelScope)

        val year = Utils.getCurrentPersianYear()
        val month = Utils.getCurrentPersianMonth()
        combine(
            transactionUseCases.getMonthTotalIncomeUseCase.invoke(year, month.second),
            transactionUseCases.getMonthTotalExpensesUseCase.invoke(year, month.second),
            transactionUseCases.hasOutcomeTransactionUseCase.invoke(),
            transactionUseCases.getMonthExpensesByAllCategoriesUseCase.invoke(year, month.second)
        ) { totalIncome, totalExpenses, hasOutcomeTransaction, categoryTransactionExpenses ->
            val formattedIncome = Utils.decimalFormatterPattern.format(totalIncome)
            val formattedExpenses = Utils.decimalFormatterPattern.format(totalExpenses)
            val formattedRemainedBalance = Utils.decimalFormatterPattern.format(totalIncome - totalExpenses)
            val categoryTransactionExpensesUiModel = categoryTransactionExpenses
                // remove categories without transaction or unimportant expens values
                .fastFilter { it.totalAmount > 10 }
                .map(CategoryTransactionExpenses::toUiOption)
            val gaugeChartProgress = calculateGaugeChartProgress(totalExpenses.toFloat(), totalIncome.toFloat())
            val gaugeChartState = calculateGaugeChartState(totalExpenses.toFloat(), totalIncome.toFloat(), hasOutcomeTransaction)
            _uiState.update {
                it.copy(
                    gaugeChartProgress = gaugeChartProgress,
                    gaugeChartState = gaugeChartState,
                    hasOutcomeTransaction = hasOutcomeTransaction,
                    monthTotalIncome = formattedIncome,
                    monthTotalExpenses = formattedExpenses,
                    remainedBalance = formattedRemainedBalance,
                    expensesByCategories = categoryTransactionExpensesUiModel,
                    persianMonthName = month.first
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeUiIntent) {
        when (event) {

            is HomeUiIntent.SaveSmsPermissionModalShownState -> saveSmsPermissionModalShownState(event.value)

        }
    }

    private fun saveSmsPermissionModalShownState(value: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSmsModalShown = value) }
            dataStoreUseCases.saveSmsModalShownStateUseCase.invoke(value)
        }
    }

    private fun calculateGaugeChartProgress(totalExpense: Float, totalIncome: Float, ): Float {
        if (totalIncome == 0.0f && totalExpense == 0.0f) return 0f
        val spendRatio = if (totalIncome > 0f) (totalExpense / totalIncome).coerceIn(0.0f, 2.0f) else 2.0f
        val progress = (spendRatio * 90.0f).coerceIn(0.0f, 180.0f)
        progress.loog("Ali", "progress -----> ")
        return progress
    }

    private fun calculateGaugeChartState(
        totalExpense: Float,
        totalIncome: Float,
        hasTransaction: Boolean
    ): GaugeChartState {
        if (!hasTransaction) return GaugeChartState.EMPTY
        return if (totalExpense >= totalIncome) GaugeChartState.RED else GaugeChartState.GREEN
    }

}

sealed interface HomeUiIntent {

    data class SaveSmsPermissionModalShownState(val value: Boolean) : HomeUiIntent

}

@Immutable
data class HomeUiState(
    val monthTotalIncome: String = "",
    val monthTotalExpenses: String = "",
    val remainedBalance: String = "",
    val persianMonthName: String = "",
    val hasOutcomeTransaction: Boolean = false,
    val formattedTotalBalance: String = "",
    val isSmsModalShown: Boolean = false,
    val gaugeChartState: GaugeChartState = GaugeChartState.EMPTY,
    val gaugeChartProgress: Float = 0f,
    val lastTransactions: List<TransactionUiModel> = emptyList(),
    val expensesByCategories: List<CategoryTransactionExpensesUiModel> = emptyList(),
    val channels: List<ChannelUiModel> = emptyList()
)