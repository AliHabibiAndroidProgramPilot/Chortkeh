package info.alihabibi.database.mappers

import info.alihabibi.database.entities.ChannelEntity
import info.alihabibi.domain.models.channel.Channel

fun ChannelEntity.asExternalModel(): Channel = Channel(
    id = id.toInt(),
    channelName = channelName,
    channelBalance = channelBalance,
    isBankCardChannel = isBankCardChannel,
    cardNumber = cardNumber,
    icon = icon,
    bankName = bankName
)

fun Channel.asEntity(): ChannelEntity = ChannelEntity(
    id = id.toLong(),
    channelName = channelName,
    channelBalance = channelBalance,
    isBankCardChannel = isBankCardChannel,
    cardNumber = cardNumber,
    icon = icon,
    bankName = bankName
)