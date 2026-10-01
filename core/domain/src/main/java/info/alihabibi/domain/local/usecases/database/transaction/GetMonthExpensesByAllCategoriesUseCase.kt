package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import kotlinx.coroutines.flow.Flow

class GetMonthExpensesByAllCategoriesUseCase(
    private val repository: TransactionRepository
) {

    operator fun invoke(year: Int, month: Int): Flow<List<CategoryTransactionExpenses>> =
        repository.getMonthExpensesByAllCategories(year, month)

}