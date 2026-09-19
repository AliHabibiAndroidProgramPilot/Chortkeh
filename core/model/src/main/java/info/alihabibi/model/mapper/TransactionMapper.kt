package info.alihabibi.model.mapper

import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.domain.models.transaction.TransactionType
import info.alihabibi.model.ui_model.transaction.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel

fun Transaction.toUiModel(): TransactionUiModel = TransactionUiModel(
    id = id,
    transactionTypeOptionUiModel = transactionType.toUiOption(),
    amount = amount,
    channel = channel.toUiModel(needsBalanceFormat = false),
    category = category.toUiModel(),
    year = year,
    month = month,
    day = day,
    time = time
)

fun TransactionUiModel.toDomain(): Transaction = Transaction(
    id = id,
    transactionType = transactionTypeOptionUiModel.toDomain(),
    amount = amount,
    channel = channel.toDomain(),
    category = category.toDomain(),
    year = year,
    month = month,
    day = day,
    time = time
)

fun TransactionType.toUiOption(): TransactionTypeOptionUiModel = when(this) {
    TransactionType.INCOME -> TransactionTypeOptionUiModel.INCOME
    TransactionType.OUTCOME -> TransactionTypeOptionUiModel.OUTCOME
}

fun TransactionTypeOptionUiModel.toDomain(): TransactionType = when(this) {
    TransactionTypeOptionUiModel.INCOME -> TransactionType.INCOME
    TransactionTypeOptionUiModel.OUTCOME -> TransactionType.OUTCOME
}