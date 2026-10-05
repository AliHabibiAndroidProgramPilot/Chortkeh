package info.alihabibi.database.repositories

import info.alihabibi.database.dao.ReminderDao
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.domain.local.repositories.ReminderRepository
import info.alihabibi.domain.models.reminder.Reminder

class ReminderRepositoryImpl(private val dao: ReminderDao) : ReminderRepository {

    override suspend fun saveReminder(reminder: Reminder): Long {
        return dao.insertReminder(reminder.asEntity())
    }

}