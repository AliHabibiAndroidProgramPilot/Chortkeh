package info.alihabibi.database.repositories

import info.alihabibi.database.dao.TransactionDao
import info.alihabibi.database.entities.CategoryTransactionExpensesData
import info.alihabibi.database.entities.DetailedTransaction
import info.alihabibi.database.entities.TransactionEntity
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.database.mappers.asExternalModel
import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val dao: TransactionDao) : TransactionRepository {

    override suspend fun saveTransaction(transaction: Transaction): Long {
        return withContext(Dispatchers.IO) {
            dao.insertTransaction(transaction.asEntity())
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.updateTransaction(transaction.asEntity())
    }

    override suspend fun deleteTransaction(transactionId: Long) {
        return withContext(Dispatchers.IO) {
            dao.deleteTransactionById(transactionId)
        }
    }

    override fun getMonthTotalIncome(year: Int, month: Int): Flow<Long> {
        return dao.getMonthTotalIncome(year, month).flowOn(Dispatchers.IO)
    }

    override fun getMonthTotalExpenses(year: Int, month: Int): Flow<Long> {
        return dao.getMonthTotalExpenses(year, month).flowOn(Dispatchers.IO)
    }

    override fun hasTransaction(): Flow<Boolean> {
        return dao.hasTransaction().flowOn(Dispatchers.IO)
    }

    override fun hasOutcomeTransaction(): Flow<Boolean> {
        return dao.hasOutcomeTransaction().flowOn(Dispatchers.IO)
    }

    override fun getMonthExpensesByAllCategories(year: Int, month: Int): Flow<List<CategoryTransactionExpenses>> {
        return dao.getMonthExpensesByAllCategories(year, month)
            .map { categoryExpensesData ->
                categoryExpensesData.map(CategoryTransactionExpensesData::asExternalModel)
            }
            .flowOn(Dispatchers.IO)
    }

    override fun getLastTransactions(count: Int, type: String): Flow<List<Transaction>> {
        return dao.getLastTransactions(count, type).map { transactionDataList ->
            transactionDataList.map(DetailedTransaction::asExternalModel)
        }
            .flowOn(Dispatchers.IO)
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { transactions ->
            transactions.map(DetailedTransaction::asExternalModel)
        }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getTransactionById(id: Long): Transaction {
        return dao.getTransactionById(id).asExternalModel()
    }

}