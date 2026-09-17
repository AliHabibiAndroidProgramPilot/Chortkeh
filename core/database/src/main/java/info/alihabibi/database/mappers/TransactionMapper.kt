package info.alihabibi.database.mappers

import info.alihabibi.database.entities.DetailedTransaction
import info.alihabibi.database.entities.TransactionEntity
import info.alihabibi.domain.models.transaction.Transaction

fun DetailedTransaction.asExternalModel(): Transaction = Transaction(
    id = transaction.id,
    transactionType = transaction.transactionType,
    amount = transaction.amount,
    channel = channel.asExternalModel(),
    category = category.asExternalModel(),
    date = transaction.date,
    time = transaction.time
)

fun Transaction.asEntity(): TransactionEntity = TransactionEntity(
    id = id,
    amount = amount,
    transactionType = transactionType,
    transactionChannelId = channel.id,
    transactionCategoryId = category.id,
    date = date,
    time = time
)