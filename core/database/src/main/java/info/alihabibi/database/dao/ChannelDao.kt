package info.alihabibi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import info.alihabibi.database.entities.ChannelEntity
import info.alihabibi.domain.Keys
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChannel(channel: ChannelEntity): Long

    @Query("SELECT * FROM ${Keys.CHANNELS_TABLE_NAME} ORDER BY id ASC")
    fun getChannels(): Flow<List<ChannelEntity>>

    @Query("DELETE FROM ${Keys.CHANNELS_TABLE_NAME} WHERE id = :id")
    suspend fun deleteChannelById(id: Long)

    @Update
    suspend fun updateChannel(channel: ChannelEntity)

    @Query("SELECT * FROM ${Keys.CHANNELS_TABLE_NAME} WHERE id = :id")
    fun getChannelById(id: Int): Flow<ChannelEntity>

    @Query("SELECT COALESCE(SUM(channelBalance), 0) FROM ${Keys.CHANNELS_TABLE_NAME}")
    fun getTotalBalance(): Flow<Long>

    @Query("""
    UPDATE ${Keys.CHANNELS_TABLE_NAME}
    SET channelBalance = channelBalance + :balance
    WHERE id = :id
""")
    suspend fun increaseChannelBalance(id: Long, balance: Long)

    @Query("""
    UPDATE ${Keys.CHANNELS_TABLE_NAME}
    SET channelBalance = channelBalance - :balance
    WHERE id = :id
""")
    suspend fun decreaseChannelBalance(id: Long, balance: Long)


}