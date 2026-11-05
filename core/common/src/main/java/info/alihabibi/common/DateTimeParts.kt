package info.alihabibi.common

data class DateTimeParts(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val dayOfWeekName: String = "",
    val hour: Int? = null,
    val minute: Int? = null
)
