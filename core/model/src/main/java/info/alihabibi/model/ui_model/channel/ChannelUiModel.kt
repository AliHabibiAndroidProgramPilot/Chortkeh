package info.alihabibi.model.ui_model.channel

data class ChannelUiModel(
    val id: Long = 0,
    val channelName: String = "",
    val channelBalance: String = "",
    val isBankCardChannel: Boolean = true,
    val cardNumber: String = "",
    val isAppDefaultChannel: Boolean = false,
    val smsSource: String? = null,
    val icon: ChannelIconOptionUiModel = ChannelIconOptionUiModel.UNKNOWN,
    val bankName: String? = null
)
