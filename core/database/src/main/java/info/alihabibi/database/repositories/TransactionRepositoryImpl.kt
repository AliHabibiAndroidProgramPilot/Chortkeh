package info.alihabibi.database.repositories

import info.alihabibi.database.dao.TransactionDao
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val dao: TransactionDao) : TransactionRepository {

    override suspend fun saveTransaction(transaction: Transaction): Long {
        return withContext(Dispatchers.IO) {
            dao.insertTransaction(transaction.asEntity())
        }
    }

    override suspend fun deleteTransaction(transactionId: Long) {
        return withContext(Dispatchers.IO) {
            dao.deleteTransactionById(transactionId)
        }
    }

    override fun getMonthTotalIncome(year: Int, month: Int): Flow<Long> {
        return dao.getMonthTotalIncome(year, month)
    }

    override fun getMonthTotalExpenses(year: Int, month: Int): Flow<Long> {
        return dao.getMonthTotalExpenses(year, month)
    }

}