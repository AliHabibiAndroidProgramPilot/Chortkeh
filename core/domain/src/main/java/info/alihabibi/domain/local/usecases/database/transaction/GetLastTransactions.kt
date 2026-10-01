package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.Transaction
import kotlinx.coroutines.flow.Flow

class GetLastTransactions(
    private val repository: TransactionRepository
) {

    operator fun invoke(count: Int, type: String): Flow<List<Transaction>> = repository.getLastTransactions(count, type)

}