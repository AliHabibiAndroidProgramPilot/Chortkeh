package info.alihabibi.domain.local.usecases.database.transaction.usecase

import info.alihabibi.domain.local.usecases.database.transaction.DeleteTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthExpensesByAllCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthTotalExpensesUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthTotalIncomeUseCase
import info.alihabibi.domain.local.usecases.database.transaction.HasOutcomeTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.SaveTransactionUseCase

data class TransactionUseCases(
    val saveTransactionUseCase: SaveTransactionUseCase,
    val deleteTransactionUseCase: DeleteTransactionUseCase,
    val getMonthTotalIncomeUseCase: GetMonthTotalIncomeUseCase,
    val getMonthTotalExpensesUseCase: GetMonthTotalExpensesUseCase,
    val hasOutcomeTransactionUseCase: HasOutcomeTransactionUseCase,
    val getMonthExpensesByAllCategoriesUseCase: GetMonthExpensesByAllCategoriesUseCase
)
