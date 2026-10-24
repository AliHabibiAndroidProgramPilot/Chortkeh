package info.alihabibi.sms.parser

import info.alihabibi.domain.models.transaction.TransactionType

data class TransactionData(
    val transactionType: TransactionType,
    val amount: Long,
    val channelId: Long,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val dayOfWeekName: String,
    val hour: Int,
    val minute: Int
)