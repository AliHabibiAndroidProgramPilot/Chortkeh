package info.alihabibi.report

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ReportViewModel(
    transactionUseCases: TransactionUseCases
) : ViewModel() {

    val uiState: StateFlow<ReportUiState>
        field = MutableStateFlow(ReportUiState())


    init {
        transactionUseCases.hasTransactionUseCases.invoke().onEach { hasTransaction ->
            uiState.update { it.copy(hasTransaction = hasTransaction) }
        }.launchIn(viewModelScope)
    }

}

@Immutable
data class ReportUiState(
    val hasTransaction: Boolean = false
)