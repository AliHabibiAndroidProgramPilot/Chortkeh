package info.alihabibi.domain.local.usecases.database.transaction.usecase

import info.alihabibi.domain.local.usecases.database.transaction.DeleteTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.SaveTransactionUseCase

data class TransactionUseCases(
    val saveTransactionUseCase: SaveTransactionUseCase,
    val deleteTransactionUseCase: DeleteTransactionUseCase
)
