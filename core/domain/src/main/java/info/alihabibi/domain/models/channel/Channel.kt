package info.alihabibi.domain.models.channel

data class Channel(
    val id: Int = 0,
    val channelName: String,
    val channelBalance: String,
    val isBankCardChannel: Boolean,
    val cardNumber: String?,
    val icon: ChannelIcon,
    val bankName: String? = null
)