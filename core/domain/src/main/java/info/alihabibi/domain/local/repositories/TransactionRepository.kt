package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.transaction.Transaction

interface TransactionRepository {

    suspend fun saveTransaction(transaction: Transaction): Long

}