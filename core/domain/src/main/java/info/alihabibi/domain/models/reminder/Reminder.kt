package info.alihabibi.domain.models.reminder

data class Reminder(
    val id: Long = 0L,
    val title: String,
    val isEnabled: Boolean,
    val triggerAtMillis: Long,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeekName: String,
    val time: String
)
