package info.alihabibi.domain.local.usecases.database.reminder.usecase

import info.alihabibi.domain.local.usecases.database.reminder.DeleteReminderUseCase
import info.alihabibi.domain.local.usecases.database.reminder.GetAllRemindersUseCase
import info.alihabibi.domain.local.usecases.database.reminder.SaveReminderUseCase
import info.alihabibi.domain.local.usecases.database.reminder.UpdateReminderIsEnabledUseCase

data class ReminderUseCases(
    val saveReminderUseCases: SaveReminderUseCase,
    val deleteReminderUseCase: DeleteReminderUseCase,
    val getAllRemindersUseCase: GetAllRemindersUseCase,
    val updateReminderIsEnabledUseCase: UpdateReminderIsEnabledUseCase
)
