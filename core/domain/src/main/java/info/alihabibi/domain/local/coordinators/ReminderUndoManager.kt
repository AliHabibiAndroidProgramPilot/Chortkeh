package info.alihabibi.domain.local.coordinators

import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import kotlinx.coroutines.launch

class ReminderUndoManager(
    private val reminderUseCases: ReminderUseCases,
    private val applicationScope: ApplicationScope
) {

    fun executeUndo(reminderId: Long) {
        applicationScope.launch {
            reminderUseCases.deleteReminderUseCase.invoke(reminderId)
        }
    }

}