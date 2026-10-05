package info.alihabibi.model.mapper

import info.alihabibi.domain.models.reminder.Reminder
import info.alihabibi.model.ui_model.reminder.ReminderUiModel

private val currentMillis = System.currentTimeMillis()

fun Reminder.toUiModel(): ReminderUiModel = ReminderUiModel(
    id = id,
    title = title,
    triggerTimeStamp = triggerAtMillis,
    isEnabled = isEnabled,
    isPassed = triggerAtMillis < currentMillis,
    year = year,
    month = month,
    day = day,
    dayOfWeekName = dayOfWeekName,
    time = time
)

fun ReminderUiModel.toDomain(): Reminder = Reminder(
    id = id,
    title = title,
    isEnabled = isEnabled,
    triggerAtMillis = triggerTimeStamp,
    year = year,
    month = month,
    day = day,
    dayOfWeekName = dayOfWeekName,
    time = time
)