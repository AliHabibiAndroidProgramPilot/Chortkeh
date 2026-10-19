package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.common.Utils
import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.chart_data.BarChartTimingOption
import info.alihabibi.domain.models.transaction.chart_data.IncomeExpenseBucket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class GetIncomeExpenseChartUseCase(
    private val repository: TransactionRepository
) {

    /**
     * @param offset 0 = current period, -1 = previous one, and so on.
     * @return a zero-filled list whose `index` is always the 0-based position of the bar:
     * YEARLY -> 12 items (فروردین/اسفند), MONTHLY -> 5 items (week 1..5), WEEKLY -> 7 items (شنبه/جمعه)
     */
    operator fun invoke(timing: BarChartTimingOption, offset: Int = 0): Flow<List<IncomeExpenseBucket>> {
        val today = LocalDate.now()
        val (currentYear, currentMonth, _) = Utils.gregorianToPersianDate(today)

        return when (timing) {

            BarChartTimingOption.YEARLY -> {
                val year = currentYear + offset
                repository.getYearBuckets(year)
                    .map { it.alignTo(keys = (1..12).toList()) }
            }

            BarChartTimingOption.MONTHLY -> {
                // normalize (year, month + offset) so that crossing a year boundary works
                val totalMonths = currentYear * 12 + (currentMonth - 1) + offset
                val year = totalMonths / 12
                val month = totalMonths % 12 + 1
                // (day - 1) / 7 is always in 0..4 for every Persian month (29..31 days)
                repository.getMonthBuckets(year, month)
                    .map { it.alignTo(keys = (0..4).toList()) }
            }

            BarChartTimingOption.WEEKLY -> {
                val weekStart = today
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
                    .plusWeeks(offset.toLong())
                // yyyyMMdd keys in Persian calendar for the 7 days, in order: Saturday .... Friday
                val dayKeys = (0L..6L).map { dayOffset ->
                    val (y, m, d) = Utils.gregorianToPersianDate(weekStart.plusDays(dayOffset))
                    y * 10_000 + m * 100 + d
                }
                repository.getDayBuckets(startKey = dayKeys.first(), endKey = dayKeys.last())
                    .map { it.alignTo(keys = dayKeys) }
            }

        }
    }

    /**
     * SQL only returns rows for buckets that have transactions.
     * This produces exactly one item per expected key (zeros for missing ones),
     * and replaces the raw SQL bucket value with the 0-based position in the chart.
     */
    private fun List<IncomeExpenseBucket>.alignTo(keys: List<Int>): List<IncomeExpenseBucket> {
        val byKey = associateBy { it.index }
        return keys.mapIndexed { position, key ->
            byKey[key]?.copy(index = position)
                ?: IncomeExpenseBucket(index = position, incomeAmount = 0L, expenseAmount = 0L)
        }
    }

}