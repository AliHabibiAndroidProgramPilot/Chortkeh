package info.alihabibi.model.ui_model.transaction

data class NewTransactionPrefill(
    val transactionType: TransactionTypeOptionUiModel,
    val amount: Long?,
    val channelId: Long,
    val year: Int?,
    val month: Int?,
    val dayOfMonth: Int?,
    val dayOfWeekName: String?,
    val hour: Int?,
    val minute: Int?
)