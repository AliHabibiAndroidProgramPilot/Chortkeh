package info.alihabibi.domain.local.usecases.database.channel

import info.alihabibi.domain.local.repositories.ChannelRepository
import info.alihabibi.domain.models.channel.Channel
import kotlinx.coroutines.flow.Flow

class GetChannelByIdUseCase(
    private val repository: ChannelRepository
) {

    operator fun invoke(id: Int): Flow<Channel> = repository.getChannelById(id)

}