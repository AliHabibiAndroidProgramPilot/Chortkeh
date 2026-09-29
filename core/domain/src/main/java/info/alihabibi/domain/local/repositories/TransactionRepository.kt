package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun saveTransaction(transaction: Transaction): Long

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transactionId: Long)

    fun getMonthTotalIncome(year: Int, month: Int): Flow<Long>

    fun getMonthTotalExpenses(year: Int, month: Int): Flow<Long>

    fun hasOutcomeTransaction(): Flow<Boolean>

    fun getMonthExpensesByAllCategories(year: Int, month: Int): Flow<List<CategoryTransactionExpenses>>

    fun getLastTransactions(count: Int, type: String): Flow<List<Transaction>>

    suspend fun getAllTransactions(): List<Transaction>

    suspend fun getTransactionById(id: Long): Transaction

}