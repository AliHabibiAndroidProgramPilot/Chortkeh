package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionId: Long) = repository.deleteTransaction(transactionId)

}