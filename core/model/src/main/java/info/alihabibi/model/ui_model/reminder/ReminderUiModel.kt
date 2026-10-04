package info.alihabibi.model.ui_model.reminder

data class ReminderUiModel(
    val id: Long = 0L,
    val title: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeekName: String,
    val time: String,
    val isPassed: Boolean,
    val isEnabled: Boolean
)
