package info.alihabibi.domain.local.usecases.database.reminder

import info.alihabibi.domain.local.repositories.ReminderRepository

class DeleteReminderUseCase(
    private val repository: ReminderRepository
) {

    suspend operator fun invoke(reminderId: Long) = repository.deleteReminder(reminderId)

}