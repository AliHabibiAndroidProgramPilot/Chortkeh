package info.alihabibi.model.mapper

import info.alihabibi.domain.models.channel.Channel
import info.alihabibi.domain.models.channel.ChannelIcon
import info.alihabibi.model.ui_model.channel.ChannelIconOptionUiModel
import info.alihabibi.model.ui_model.channel.ChannelUiModel

fun Channel.toUiModel(): ChannelUiModel = ChannelUiModel(
    id = id,
    channelName = channelName,
    channelBalance = channelBalance.toString(),
    isBankCardChannel = isBankCardChannel,
    cardNumber = cardNumber.orEmpty(),
    isAppDefaultChannel = isAppDefaultChannel,
    icon = icon.toUiOption(),
    bankName = bankName
)

fun ChannelUiModel.toDomain(): Channel = Channel(
    id = id,
    channelName = channelName,
    channelBalance = channelBalance.toLongOrNull() ?: 0L,
    isBankCardChannel = isBankCardChannel,
    cardNumber = cardNumber,
    isAppDefaultChannel = isAppDefaultChannel,
    icon = icon.toDomain(),
    bankName = bankName
)

fun ChannelIcon.toUiOption(): ChannelIconOptionUiModel = ChannelIconOptionUiModel.valueOf(name)

fun ChannelIconOptionUiModel.toDomain(): ChannelIcon = ChannelIcon.valueOf(name)