package info.alihabibi.domain.local.usecases.database.reminder

import info.alihabibi.domain.local.repositories.ReminderRepository

class UpdateReminderIsEnabledUseCase(
    private val repository: ReminderRepository
) {

    suspend operator fun invoke(reminderId: Long, isEnabled: Boolean) =
        repository.updateReminderIsEnabled(reminderId, isEnabled)

}
