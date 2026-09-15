package info.alihabibi.database.repositories

import info.alihabibi.database.dao.TransactionDao
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val dao: TransactionDao) : TransactionRepository {

    override suspend fun insertTransaction(transaction: Transaction): Long {
        return withContext(Dispatchers.IO) {
            dao.insertTransaction(transaction.asEntity())
        }
    }

}