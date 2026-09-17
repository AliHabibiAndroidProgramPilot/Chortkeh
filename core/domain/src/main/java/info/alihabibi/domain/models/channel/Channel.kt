package info.alihabibi.domain.models.channel

data class Channel(
    val id: Long = 0,
    val channelName: String,
    val channelBalance: Long,
    val isBankCardChannel: Boolean,
    val cardNumber: String?,
    val isAppDefaultChannel: Boolean = false,
    val icon: ChannelIcon,
    val bankName: String? = null
)