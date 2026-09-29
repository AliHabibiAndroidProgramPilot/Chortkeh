package info.alihabibi.domain.local.usecases.database.transaction.usecase

import info.alihabibi.domain.local.usecases.database.transaction.DeleteTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetAllTransactionsUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetLastTransactions
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthExpensesByAllCategoriesUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthTotalExpensesUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetMonthTotalIncomeUseCase
import info.alihabibi.domain.local.usecases.database.transaction.GetTransactionByIdUseCase
import info.alihabibi.domain.local.usecases.database.transaction.HasOutcomeTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.SaveTransactionUseCase
import info.alihabibi.domain.local.usecases.database.transaction.UpdateTransactionUseCase

data class TransactionUseCases(
    val saveTransactionUseCase: SaveTransactionUseCase,
    val updateTransactionUseCase: UpdateTransactionUseCase,
    val deleteTransactionUseCase: DeleteTransactionUseCase,
    val getMonthTotalIncomeUseCase: GetMonthTotalIncomeUseCase,
    val getMonthTotalExpensesUseCase: GetMonthTotalExpensesUseCase,
    val hasOutcomeTransactionUseCase: HasOutcomeTransactionUseCase,
    val getMonthExpensesByAllCategoriesUseCase: GetMonthExpensesByAllCategoriesUseCase,
    val getLastTransactions: GetLastTransactions,
    val getAllTransactions: GetAllTransactionsUseCase,
    val getTransactionByIdUseCase: GetTransactionByIdUseCase
)
