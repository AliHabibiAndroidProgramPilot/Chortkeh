package info.alihabibi.model.mapper

import info.alihabibi.domain.models.channel.Channel
import info.alihabibi.domain.models.channel.ChannelIcon
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.model.ui_model.channel.ChannelUiModel

fun Channel.toUiModel(): ChannelUiModel = ChannelUiModel(
    id = id,
    channelName = channelName,
    channelBalance = channelBalance,
    isBankCardChannel = isBankCardChannel,
    cardNumber = cardNumber.orEmpty(),
    icon = icon.toUiOption()
)

fun ChannelUiModel.toDomain(): Channel = Channel(
    id = id,
    channelName = channelName,
    channelBalance = channelBalance,
    isBankCardChannel = isBankCardChannel,
    cardNumber = cardNumber,
    icon = icon.toDomain()
)

fun ChannelIcon.toUiOption(): ChannelIconOptionUiModel = ChannelIconOptionUiModel.valueOf(name)

fun ChannelIconOptionUiModel.toDomain(): ChannelIcon = ChannelIcon.valueOf(name)