package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import info.alihabibi.database.entities.ChannelEntity
import info.alihabibi.domain.local.keys.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChannel(channel: ChannelEntity): Long

    @Query("SELECT * FROM ${Keys.CHANNELS_TABLE_NAME} ORDER BY id ASC")
    fun getChannels(): Flow<List<ChannelEntity>>

    @Delete
    suspend fun deleteChannel(channel: ChannelEntity)

    @Update
    suspend fun updateChannel(channel: ChannelEntity)

}