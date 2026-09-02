package info.alihabibi.domain.local.defaults

import info.alihabibi.domain.models.channel.Channel
import info.alihabibi.domain.models.channel.ChannelIcon

object DefaultChannels {

    val channel = Channel(
        channelName = "همه کانال ها",
        channelBalance = 0L,
        isBankCardChannel = false,
        cardNumber = null,
        isAppDefaultChannel = true,
        icon = ChannelIcon.UNKNOWN
    )

}