package info.alihabibi.domain.local.usecases.database.transaction

import info.alihabibi.common.Utils
import info.alihabibi.domain.local.repositories.TransactionRepository
import info.alihabibi.domain.models.transaction.CategoryTransactionExpenses
import info.alihabibi.domain.models.transaction.TransactionType
import info.alihabibi.domain.models.transaction.chart_data.BarChartTimingOption
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class GetCategoryExpensesUseCase(
    private val repository: TransactionRepository
) {

    operator fun invoke(
        timing: BarChartTimingOption,
        type: TransactionType,
        offset: Int = 0
    ): Flow<List<CategoryTransactionExpenses>> {
        val today = LocalDate.now()
        val (currentYear, currentMonth, _) = Utils.gregorianToPersianDate(today)

        return when (timing) {

            BarChartTimingOption.YEARLY -> {
                val year = currentYear + offset
                repository.getYearCategoryExpenses(year, type.name)
            }

            BarChartTimingOption.MONTHLY -> {
                val totalMonths = currentYear * 12 + (currentMonth - 1) + offset
                val year = totalMonths / 12
                val month = totalMonths % 12 + 1
                repository.getMonthCategoryExpenses(year, month, type.name)
            }

            BarChartTimingOption.WEEKLY -> {
                val weekStart = today
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))
                    .plusWeeks(offset.toLong())
                val dayKeys = (0L..6L).map { dayOffset ->
                    val (y, m, d) = Utils.gregorianToPersianDate(weekStart.plusDays(dayOffset))
                    y * 10_000 + m * 100 + d
                }
                repository.getWeeklyCategoryExpenses(
                    startKey = dayKeys.first(),
                    endKey = dayKeys.last(),
                    type = type.name
                )
            }

        }
    }

}
