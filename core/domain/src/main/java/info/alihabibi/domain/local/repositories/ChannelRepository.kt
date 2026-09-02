package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.channel.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    fun getChannels(): Flow<List<Channel>>

    suspend fun saveChannel(channel: Channel): Long

    suspend fun deleteChannel(channels: List<Channel>)

    suspend fun updateChannel(channel: Channel)

    fun getTotalBalance(): Flow<Long>

}