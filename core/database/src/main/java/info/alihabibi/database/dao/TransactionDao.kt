package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.alihabibi.database.entities.TransactionEntity
import info.alihabibi.domain.Keys

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query("DELETE FROM ${Keys.TRANSACTION_TABLE_NAME} WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)

}