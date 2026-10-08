package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.reminder.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun saveReminder(reminder: Reminder): Long

    suspend fun deleteReminder(reminderId: Long)

    fun getAllReminders(): Flow<List<Reminder>>

    suspend fun updateReminderIsEnabled(reminderId: Long, isEnabled: Boolean)

}