package info.alihabibi.domain.models.transaction.chart_data

data class IncomeExpenseChart(
    val chartPeriod: BarChartTimingOption,
    val buckets: List<IncomeExpenseBucket>
)