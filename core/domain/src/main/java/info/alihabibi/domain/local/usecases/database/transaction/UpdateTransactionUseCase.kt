package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.Transaction

class UpdateTransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: Transaction) = repository.updateTransaction(transaction)

}