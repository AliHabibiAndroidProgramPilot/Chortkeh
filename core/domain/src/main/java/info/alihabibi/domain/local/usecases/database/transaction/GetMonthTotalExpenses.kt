package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetMonthTotalExpenses(
    private val repository: TransactionRepository
) {

    operator fun invoke(year: Int, month: Int): Flow<Long> = repository.getMonthTotalExpenses(year, month)

}