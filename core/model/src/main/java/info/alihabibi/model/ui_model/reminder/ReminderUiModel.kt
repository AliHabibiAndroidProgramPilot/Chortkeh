package info.alihabibi.model.ui_model.reminder

data class ReminderUiModel(
    val id: Long = 0L,
    val title: String,
    val triggerTimeStamp: Long,
    val isEnabled: Boolean,
    val isPassed: Boolean?,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeekName: String,
    val time: String
)
