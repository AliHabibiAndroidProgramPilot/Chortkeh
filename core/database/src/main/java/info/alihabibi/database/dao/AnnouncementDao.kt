package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.alihabibi.database.entities.AnnouncementEntity
import info.alihabibi.domain.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity): Long

    @Query("SELECT * FROM ${Keys.ANNOUNCEMENT_TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getAnnouncementById(id: Long): Flow<AnnouncementEntity?>

}
