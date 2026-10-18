package info.alihabibi.model.mapper

import info.alihabibi.common.Utils
import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.domain.models.transaction.TransactionType
import info.alihabibi.domain.models.transaction.chart_data.BarChartTimingOption
import info.alihabibi.domain.models.transaction.chart_data.IncomeExpenseBucket
import info.alihabibi.model.ui_model.transaction.BarChartTimingOptionUiModel
import info.alihabibi.model.ui_model.transaction.CategoryTransactionExpensesUiModel
import info.alihabibi.model.ui_model.transaction.TransactionTypeOptionUiModel
import info.alihabibi.model.ui_model.transaction.TransactionUiModel
import info.alihabibi.model.ui_model.transaction.chart_data.IncomeExpenseBucketUiModel

fun Transaction.toUiModel(needsAmountFormat: Boolean = true): TransactionUiModel = TransactionUiModel(
    id = id,
    type = transactionType.toUiOption(),
    amount = if (needsAmountFormat) Utils.decimalFormatterPattern.format(amount) else amount.toString(),
    channel = channel?.toUiModel(false),
    category = category?.toUiModel(),
    year = year,
    month = month,
    day = day,
    dayOfWeekName = dayOfWeekName,
    time = time
)

fun TransactionUiModel.toDomain(): Transaction = Transaction(
    id = id,
    transactionType = type.toDomain(),
    amount = amount.toLongOrNull() ?: 0L,
    channel = channel?.toDomain(),
    category = category?.toDomain(),
    year = year,
    month = month,
    day = day,
    dayOfWeekName = dayOfWeekName,
    time = time
)

fun CategoryTransactionExpenses.toUiOption(): CategoryTransactionExpensesUiModel = CategoryTransactionExpensesUiModel(
    categoryId = categoryId,
    categoryTitle = categoryTitle,
    totalAmount = totalAmount.toFloat()
)

fun TransactionType.toUiOption(): TransactionTypeOptionUiModel = when(this) {
    TransactionType.INCOME -> TransactionTypeOptionUiModel.INCOME
    TransactionType.OUTCOME -> TransactionTypeOptionUiModel.OUTCOME
}

fun TransactionTypeOptionUiModel.toDomain(): TransactionType = when(this) {
    TransactionTypeOptionUiModel.INCOME -> TransactionType.INCOME
    TransactionTypeOptionUiModel.OUTCOME -> TransactionType.OUTCOME
}

fun BarChartTimingOptionUiModel.toDomain(): BarChartTimingOption = when(this) {
    BarChartTimingOptionUiModel.WEEKLY -> BarChartTimingOption.WEEKLY
    BarChartTimingOptionUiModel.MONTHLY -> BarChartTimingOption.MONTHLY
     BarChartTimingOptionUiModel.YEARLY -> BarChartTimingOption.YEARLY
}

fun IncomeExpenseBucket.toUiModel(): IncomeExpenseBucketUiModel = IncomeExpenseBucketUiModel(
    index = index,
    expenseAmount = expenseAmount,
    incomeAmount = incomeAmount
)