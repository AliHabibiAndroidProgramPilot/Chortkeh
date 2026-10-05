package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.reminder.Reminder

interface ReminderRepository {

    suspend fun saveReminder(reminder: Reminder): Long

}