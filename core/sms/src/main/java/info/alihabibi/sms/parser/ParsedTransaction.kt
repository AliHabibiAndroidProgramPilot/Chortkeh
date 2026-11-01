package info.alihabibi.sms.parser

import info.alihabibi.domain.models.transaction.TransactionType

data class ParsedTransaction(
    val transactionType: TransactionType,
    val amount: Long
)
