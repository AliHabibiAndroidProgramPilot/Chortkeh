package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow

class HasTransactionUseCase(
    private val repository: TransactionRepository
) {

    operator fun invoke(): Flow<Boolean> = repository.hasTransaction()

}