package info.alihabibi.domain.local.usecases.database.channel.usecase

import info.alihabibi.domain.local.usecases.database.channel.DeleteChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.GetChannelsUseCase
import info.alihabibi.domain.local.usecases.database.channel.SaveChannelUseCase
import info.alihabibi.domain.local.usecases.database.channel.UpdateChannelUseCase

data class ChannelUseCases(
    val saveChannelUseCase: SaveChannelUseCase,
    val getChannelsUseCase: GetChannelsUseCase,
    val deleteChannelUseCase: DeleteChannelUseCase,
    val updateChannelUseCase: UpdateChannelUseCase
)
