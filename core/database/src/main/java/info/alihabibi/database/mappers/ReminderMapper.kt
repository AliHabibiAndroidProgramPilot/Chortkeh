package info.alihabibi.database.mappers

import info.alihabibi.database.entities.ReminderEntity
import info.alihabibi.domain.models.reminder.Reminder

fun ReminderEntity.asExternalModel(): Reminder = Reminder(
    id = id,
    title = title,
    isEnabled = isEnabled,
    triggerAtMillis = triggerAtTimeStamp,
    year = year,
    month = month,
    day = day,
    dayOfWeekName = dayOfWeekName,
    time = time
)

fun Reminder.asEntity(): ReminderEntity = ReminderEntity(
    id = id,
    title = title,
    isEnabled = isEnabled,
    triggerAtTimeStamp = triggerAtMillis,
    year = year,
    month = month,
    day = day,
    dayOfWeekName = dayOfWeekName,
    time = time
)