package info.alihabibi.domain.local.usecases.database.channel

import info.alihabibi.domain.local.repositories.ChannelRepository
import info.alihabibi.domain.models.channel.Channel

class UpdateChannelUseCase(
    private val repository: ChannelRepository
) {

    suspend operator fun invoke(channel: Channel) = repository.updateChannel(channel)

}