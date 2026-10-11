package info.alihabibi.domain.local.usecases.database.reminder

import info.alihabibi.domain.local.repositories.ReminderRepository
import info.alihabibi.domain.models.reminder.Reminder
import kotlinx.coroutines.flow.Flow

class GetAllRemindersUseCase(
    private val repository: ReminderRepository
) {

    operator fun invoke(): Flow<List<Reminder>> = repository.getAllReminders()

}