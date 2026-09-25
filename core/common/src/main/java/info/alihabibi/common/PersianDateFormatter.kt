package info.alihabibi.common

object PersianDateFormatter {

    val persianMonths = listOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )

    fun format(year: Int, month: Int, day: Int): String {
        require(month in 1..12) { return "" }
        require(day in 1..31) { return "" }
        return "$day ${persianMonths[month - 1]} $year"
    }

    fun format(year: Int, month: Int, dayOfWeekName: String, dayOfMonth: Int, time: String): String {
        require(month in 1..12) { return "" }
        return StringBuilder()
            .append(dayOfWeekName)
            .append(", ")
            .append("$dayOfMonth ")
            .append("${persianMonths[month -1]} ")
            .append(year)
            .append(", ")
            .append(time)
            .toString()
    }

}