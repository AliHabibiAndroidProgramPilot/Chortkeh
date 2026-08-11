package info.alihabibi.model.ui_model.channel

data class ChannelUiModel(
    val id: Int = 0,
    val channelName: String = "",
    val channelBalance: String = "",
    val icon: ChannelIconOptionUiModel = ChannelIconOptionUiModel.DEFAULT
)
