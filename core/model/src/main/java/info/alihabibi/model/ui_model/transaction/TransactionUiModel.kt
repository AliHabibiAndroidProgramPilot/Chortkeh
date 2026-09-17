package info.alihabibi.model.ui_model.transaction

import info.alihabibi.model.ui_model.category.CategoryUiModel
import info.alihabibi.model.ui_model.channel.ChannelUiModel

data class TransactionUiModel(
    val id: Long = 0L,
    val transactionTypeOptionUiModel: TransactionTypeOptionUiModel,
    val amount: Long,
    val channel: ChannelUiModel,
    val category: CategoryUiModel,
    val date: String,
    val time: String
)