package info.alihabibi.home

import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AllTransactionsViewModel(transactionsUseCases: TransactionUseCases) : ViewModel() {

    private val _transactions = MutableStateFlow(listOf<TransactionUiModel>())
    val transactions: StateFlow<List<TransactionUiModel>> = _transactions.asStateFlow()

    init {
        transactionsUseCases.getAllTransactions.invoke().onEach { transactions ->
            val transactionsUiModel = transactions.fastMap(Transaction::toUiModel)
            _transactions.value = transactionsUiModel
        }.launchIn(viewModelScope)
    }

}