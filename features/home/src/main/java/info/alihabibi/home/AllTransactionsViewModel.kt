package info.alihabibi.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllTransactionsViewModel(
    private val transactionsUseCases: TransactionUseCases
) : ViewModel() {

    private val _transactions = MutableStateFlow(listOf<TransactionUiModel>())
    val transactions: StateFlow<List<TransactionUiModel>> = _transactions.asStateFlow()

    init {
        viewModelScope.launch {
            val transactions = transactionsUseCases.getAllTransactions.invoke().map(Transaction::toUiModel)
            _transactions.value = transactions
        }
    }

}