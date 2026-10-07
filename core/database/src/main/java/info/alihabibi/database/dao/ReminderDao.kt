package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.alihabibi.database.entities.ReminderEntity
import info.alihabibi.domain.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Query("DELETE FROM ${Keys.REMINDER_TABLE_NAME} WHERE id = :id")
    suspend fun deleteReminder(id: Long)

    @Query("SELECT * FROM ${Keys.REMINDER_TABLE_NAME} ORDER BY id DESC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

}