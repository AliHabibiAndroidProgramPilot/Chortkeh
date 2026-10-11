package info.alihabibi.common

import android.content.Context
import android.util.Log
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {

    val decimalFormatterPattern = DecimalFormat("#,###")

    fun <T> T.loog(tag: String = "loog", param: String = "param"): T {
        return this.apply {
            Log.d(tag, "$param = $this")
        }
    }

    /**
     * Converts a Gregorian (Miladi) date to a Persian (Jalali/Shamsi) date.
     *
     * This implementation is based on a well-known conversion algorithm and was
     * initially generated with AI, then reviewed and integrated into the project.
     *
     * Note:
     * - Expects valid Gregorian year, month (1-12), and day values.
     * - Returns an IntArray in the format: [year, month, day].
     * - If future calendar requirements become more complex, consider replacing
     *   this implementation with a well-tested date/time library.
     */
    fun gregorianToPersianDate(date: LocalDate): DateTimeParts {
        val gDaysBeforeMonth = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (date.monthValue > 2) date.year + 1 else date.year
        var days = 355666 + (365 * date.year) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) +
                ((gy2 + 399) / 400) + date.dayOfMonth + gDaysBeforeMonth[date.monthValue - 1]

        var jy = -1595 + (33 * (days / 12053))
        days %= 12053
        jy += 4 * (days / 1461)
        days %= 1461

        if (days > 365) {
            jy += (days - 1) / 365
            days = (days - 1) % 365
        }

        val jm: Int
        val jd: Int
        if (days < 186) {
            jm = 1 + (days / 31)
            jd = 1 + (days % 31)
        } else {
            jm = 7 + ((days - 186) / 30)
            jd = 1 + ((days - 186) % 30)
        }

        return DateTimeParts(jy, jm, jd)
    }

    fun getCurrentPersianMonth(): Pair<String, Int> {
        val date = Instant
            .ofEpochMilli(System.currentTimeMillis())
            .atZone(ZoneId.systemDefault())
        val (_, month, _) = gregorianToPersianDate(date.toLocalDate())
        return Pair(PersianDateFormatter.persianMonths[month - 1], month)
    }

    fun getCurrentPersianDate(): Triple<Int, Int, Int> {
        val date = Instant
            .ofEpochMilli(System.currentTimeMillis())
            .atZone(ZoneId.systemDefault())
        val (year, month, day) = gregorianToPersianDate(date.toLocalDate())
        return Triple(year, month, day)
    }

    fun mergeTimeIntoEpochMillis(epochMillis: Long, time: String): Long {
        val instant = Instant.ofEpochMilli(epochMillis)
        val dateTime = instant
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val localTime = LocalTime.parse(
            time.replace(" ", ""),
            DateTimeFormatter.ofPattern("HH:mm")
        )

        return dateTime
            .with(localTime)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun getAppVersionName(context: Context): String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()

    fun getStringResources(context: Context, id: Int): String = context.getString(id)

}