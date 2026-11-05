package info.alihabibi.domain.models.transaction.chart_data

data class IncomeExpenseBucket(
    val index: Int,
    val incomeAmount: Long,
    val expenseAmount: Long
)