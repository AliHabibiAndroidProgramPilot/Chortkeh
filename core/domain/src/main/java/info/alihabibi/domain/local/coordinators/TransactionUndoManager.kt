package info.alihabibi.domain.local.coordinators

import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.transaction.usecase.TransactionUseCases
import kotlinx.coroutines.launch

class TransactionUndoManager(
    private val transactionUseCases: TransactionUseCases,
    private val applicationScope: ApplicationScope
) {

    fun executeUndo(transactionId: Long) {
        applicationScope.launch {
            transactionUseCases.deleteTransactionUseCase.invoke(transactionId)
        }
    }

}