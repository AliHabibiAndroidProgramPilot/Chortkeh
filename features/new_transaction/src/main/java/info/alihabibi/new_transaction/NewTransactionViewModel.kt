package info.alihabibi.new_transaction

import androidx.lifecycle.ViewModel
import info.alihabibi.model.ui_model.TransactionTypeOptionUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewTransactionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewTransactionUiState())
    val uiState: StateFlow<NewTransactionUiState> = _uiState.asStateFlow()

    fun onEvent(event: NewTransactionUiIntent) {
        when(event) {

            is NewTransactionUiIntent.OnPriceChanged -> changePrice(event.price)

            is NewTransactionUiIntent.OnTransactionTypeChanged -> changeTransactionType(event.type)

        }
    }

    private fun changePrice(price: String) {
        val digits = price
            .filter(Char::isDigit)
            .take(17)
            .trimStart('0')
        _uiState.update {
            it.copy(transactionPrice = digits)
        }
    }

    private fun changeTransactionType(type: TransactionTypeOptionUiModel) {
        _uiState.update {
            it.copy(transactionType = type)
        }
    }

}

sealed interface NewTransactionUiIntent {

    data class OnPriceChanged(val price: String) : NewTransactionUiIntent

    data class OnTransactionTypeChanged(val type: TransactionTypeOptionUiModel) : NewTransactionUiIntent

}

data class NewTransactionUiState(
    val transactionType: TransactionTypeOptionUiModel = TransactionTypeOptionUiModel.INCOME,
    val transactionPrice: String = ""
)