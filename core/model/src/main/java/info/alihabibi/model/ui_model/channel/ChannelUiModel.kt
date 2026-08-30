package info.alihabibi.model.ui_model.channel

data class ChannelUiModel(
    val id: Int = 0,
    val channelName: String = "",
    val channelBalance: String = "",
    val isBankCardChannel: Boolean = true,
    val cardNumber: String = "",
    val isAppDefaultChannel: Boolean = false,
    val icon: ChannelIconOptionUiModel = ChannelIconOptionUiModel.DEFAULT
)
