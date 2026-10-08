package info.alihabibi.database.repositories

import info.alihabibi.database.dao.ReminderDao
import info.alihabibi.database.entities.ReminderEntity
import info.alihabibi.database.mappers.asEntity
import info.alihabibi.database.mappers.asExternalModel
import info.alihabibi.domain.local.repositories.ReminderRepository
import info.alihabibi.domain.models.reminder.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ReminderRepositoryImpl(private val dao: ReminderDao) : ReminderRepository {

    override suspend fun saveReminder(reminder: Reminder): Long {
        return withContext(Dispatchers.IO) {
            dao.insertReminder(reminder.asEntity())
        }
    }

    override suspend fun deleteReminder(reminderId: Long) {
        withContext(Dispatchers.IO) {
            dao.deleteReminder(reminderId)
        }
    }

    override fun getAllReminders(): Flow<List<Reminder>> {
        return dao.getAllReminders()
            .map { entities -> entities.map(ReminderEntity::asExternalModel) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun updateReminderIsEnabled(reminderId: Long, isEnabled: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateReminderIsEnabled(reminderId, isEnabled)
        }
    }

}