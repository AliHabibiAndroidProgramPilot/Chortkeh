package info.alihabibi.domain.local.usecases.database.reminder.usecase

import info.alihabibi.domain.local.usecases.database.reminder.DeleteReminderUseCase
import info.alihabibi.domain.local.usecases.database.reminder.GetAllRemindersUseCase
import info.alihabibi.domain.local.usecases.database.reminder.GetReminderByIdUseCase
import info.alihabibi.domain.local.usecases.database.reminder.SaveReminderUseCase
import info.alihabibi.domain.local.usecases.database.reminder.UpdateReminderIsEnabledUseCase
import info.alihabibi.domain.local.usecases.database.reminder.UpdateReminderUseCase

data class ReminderUseCases(
    val saveReminderUseCases: SaveReminderUseCase,
    val updateReminderUseCase: UpdateReminderUseCase,
    val deleteReminderUseCase: DeleteReminderUseCase,
    val getAllRemindersUseCase: GetAllRemindersUseCase,
    val updateReminderIsEnabledUseCase: UpdateReminderIsEnabledUseCase,
    val getReminderByIdUseCase: GetReminderByIdUseCase
)
