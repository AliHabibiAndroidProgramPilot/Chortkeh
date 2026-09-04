package info.alihabibi.database.repositories

import info.alihabibi.database.dao.ChannelDao
import info.alihabibi.database.entities.ChannelEntity
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.database.mappers.asExternalModel
import info.alihabibi.domain.local.repositories.ChannelRepository
import info.alihabibi.domain.models.channel.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ChannelRepositoryImpl(private val dao: ChannelDao) : ChannelRepository {

    override fun getChannels(): Flow<List<Channel>> {
        return dao.getChannels()
            .map { entities -> entities.map(ChannelEntity::asExternalModel) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun saveChannel(channel: Channel): Long {
        return withContext(Dispatchers.IO) {
            dao.insertChannel(channel.asEntity())
        }
    }

    override suspend fun deleteChannel(channels: Channel) {
        withContext(Dispatchers.IO) {
            val channelEntity = channels.asEntity()
            dao.deleteChannel(channelEntity)
        }
    }

    override suspend fun updateChannel(channel: Channel) {
        withContext(Dispatchers.IO) {
            dao.updateChannel(channel.asEntity())
        }
    }

    override fun getChannelById(id: Int): Flow<Channel> {
        return dao.getChannelById(id)
            .map { it.asExternalModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun getTotalBalance(): Flow<Long> {
        return dao.getTotalBalance()
    }

}