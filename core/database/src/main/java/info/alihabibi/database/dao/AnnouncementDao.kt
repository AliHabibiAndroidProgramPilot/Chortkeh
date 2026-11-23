package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import info.alihabibi.database.entities.AnnouncementEntity
import info.alihabibi.database.entities.DetailedAnnouncementTransaction
import info.alihabibi.database.entities.DetailedAnnouncement
import info.alihabibi.domain.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity): Long

    @Transaction
    @Query(
        """
    SELECT 
        tx_id, 
        tx_transactionType, 
        tx_amount, 
        tx_transactionChannelId,
        tx_transactionCategoryId,
        tx_year, 
        tx_month, 
        tx_day, 
        tx_dayOfWeekName, 
        tx_time 
    FROM ${Keys.ANNOUNCEMENT_TABLE_NAME} 
    WHERE id = :id 
    LIMIT 1
"""
    )
    suspend fun getTransactionByAnnouncementId(id: Long): DetailedAnnouncementTransaction?

    @Transaction
    @Query("SELECT * FROM ${Keys.ANNOUNCEMENT_TABLE_NAME} ORDER BY id DESC LIMIT 200")
    fun getAllSmsAnnouncements(): Flow<List<DetailedAnnouncement>>

    @Query("SELECT EXISTS(SELECT 1 FROM ${Keys.ANNOUNCEMENT_TABLE_NAME} WHERE announcementType = 'BANK_SMS')")
    suspend fun hasSmsAnnouncements(): Boolean

    @Query("DELETE FROM ${Keys.ANNOUNCEMENT_TABLE_NAME} WHERE id = :id")
    suspend fun deleteAnnouncementById(id: Long)

}
