package info.alihabibi.domain.models.transaction

import info.alihabibi.domain.models.category.Category
import info.alihabibi.domain.models.channel.Channel

data class Transaction(
    val id: Long = 0L,
    val transactionType: TransactionType,
    val amount: Long,
    val channel: Channel?,
    val category: Category?,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeekName: String,
    val time: String
)