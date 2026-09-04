package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.channel.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    fun getChannels(): Flow<List<Channel>>

    suspend fun saveChannel(channel: Channel): Long

    suspend fun deleteChannel(channels: Channel)

    suspend fun updateChannel(channel: Channel)

    fun getChannelById(id: Int): Flow<Channel>

    fun getTotalBalance(): Flow<Long>

}