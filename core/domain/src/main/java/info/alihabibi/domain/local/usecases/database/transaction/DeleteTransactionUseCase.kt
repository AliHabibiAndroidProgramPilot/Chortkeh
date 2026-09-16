package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.Transaction

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: Transaction) = repository.deleteTransaction(transaction)

}