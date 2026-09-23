package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.alihabibi.database.entities.CategoryTransactionExpensesData
import info.alihabibi.database.entities.TransactionEntity
import info.alihabibi.domain.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("DELETE FROM ${Keys.TRANSACTION_TABLE_NAME} WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Query("""
    SELECT COALESCE(SUM(amount), 0)
    FROM ${Keys.TRANSACTION_TABLE_NAME}
    WHERE transactionType = 'INCOME'
      AND year = :year
      AND month = :month
""")
    fun getMonthTotalIncome(year: Int, month: Int): Flow<Long>

    @Query("""
    SELECT COALESCE(SUM(amount), 0)
    FROM ${Keys.TRANSACTION_TABLE_NAME}
    WHERE transactionType = 'OUTCOME'
      AND year = :year
      AND month = :month
""")
    fun getMonthTotalExpenses(year: Int, month: Int): Flow<Long>

    @Query("SELECT EXISTS( SELECT 1 FROM ${Keys.TRANSACTION_TABLE_NAME} WHERE transactionType = 'OUTCOME')")
    fun hasOutcomeTransaction(): Flow<Boolean>

    @Query("""
    SELECT
        c.id AS categoryId,
        c.title AS categoryTitle,
        COALESCE(SUM(t.amount), 0) AS totalAmount
    FROM ${Keys.CATEGORY_TABLE_NAME} c
    LEFT JOIN ${Keys.TRANSACTION_TABLE_NAME} t
        ON t.transactionCategoryId = c.id
        AND t.year = :year
        AND t.month = :month
        WHERE c.type = 'OUTCOME'
    GROUP BY c.id
""")
    fun getMonthExpensesByAllCategories(year: Int, month: Int): Flow<List<CategoryTransactionExpensesData>>

}