package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.domain.models.transaction.chart_data.IncomeExpenseBucket
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun saveTransaction(transaction: Transaction): Long

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transactionId: Long)

    fun getMonthTotalIncome(year: Int, month: Int): Flow<Long>

    fun getMonthTotalExpenses(year: Int, month: Int): Flow<Long>

    fun hasTransaction(): Flow<Boolean>

    fun hasOutcomeTransaction(): Flow<Boolean>

    fun getMonthExpensesByAllCategories(year: Int, month: Int): Flow<List<CategoryTransactionExpenses>>

    fun getYearCategoryExpenses(year: Int, type: String): Flow<List<CategoryTransactionExpenses>>

    fun getMonthCategoryExpenses(year: Int, month: Int, type: String): Flow<List<CategoryTransactionExpenses>>

    fun getWeeklyCategoryExpenses(startKey: Int, endKey: Int, type: String): Flow<List<CategoryTransactionExpenses>>

    fun getLastTransactions(count: Int, type: String?): Flow<List<Transaction>>

    fun getAllTransactions(): Flow<List<Transaction>>

    suspend fun getTransactionById(id: Long): Transaction

    fun getYearBuckets(year: Int): Flow<List<IncomeExpenseBucket>>

    fun getMonthBuckets(year: Int, month: Int): Flow<List<IncomeExpenseBucket>>

    fun getDayBuckets(startKey: Int, endKey: Int): Flow<List<IncomeExpenseBucket>>

}