package info.alihabibi.domain.local.usecases.database.reminder

import info.alihabibi.domain.local.repositories.ReminderRepository
import info.alihabibi.domain.models.reminder.Reminder

class SaveReminderUseCase(private val repository: ReminderRepository) {

    suspend operator fun invoke(reminder: Reminder): Long = repository.saveReminder(reminder)

}