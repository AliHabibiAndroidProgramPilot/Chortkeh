package info.alihabibi.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.Utils
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
                .fastFilter { it.totalAmount > 1000 }
                .map(CategoryTransactionExpenses::toUiOption)
            _uiState.update {
                it.copy(
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
    val lastTransactions: List<TransactionUiModel> = emptyList(),
    val expensesByCategories: List<CategoryTransactionExpensesUiModel> = emptyList(),
    val channels: List<ChannelUiModel> = emptyList()
)