package info.alihabibi.common

import android.content.Context
import android.util.Log
import java.util.Calendar

object Utils {

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
    private fun gregorianToPersianDate(year: Int, month: Int, day: Int): IntArray {
        val gDaysBeforeMonth = intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        val gy2 = if (month > 2) year + 1 else year
        var days = 355666 + (365 * year) + ((gy2 + 3) / 4) - ((gy2 + 99) / 100) +
                ((gy2 + 399) / 400) + day + gDaysBeforeMonth[month - 1]

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

        return intArrayOf(jy, jm, jd)
    }

    fun getCurrentPersianMonth(): String {
        val cal = Calendar.getInstance()
        val (_, jm, _) = gregorianToPersianDate(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH)
        )
        val month = listOf(
            "فروردین ماه", "اردیبهشت ماه", "خرداد ماه", "تیر ماه", "مرداد ماه", "شهریور ماه",
            "مهر ماه", "آبان ماه", "آذر ماه", "دی ماه", "بهمن ماه", "اسفند ماه"
        )
        return month[jm - 1]
    }

    fun getAppVersionName(context: Context): String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()

    fun getStringResources(context: Context, id: Int): String = context.getString(id)

}