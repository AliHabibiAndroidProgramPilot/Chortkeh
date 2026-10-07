package info.alihabibi.reminder.viewmodels

import androidx.compose.ui.util.fastMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import info.alihabibi.domain.models.reminder.Reminder
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.reminder.ReminderUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ReminderListViewModel(reminderUseCases: ReminderUseCases) : ViewModel() {

    val reminders: StateFlow<List<ReminderUiModel>>
        field = MutableStateFlow(emptyList<ReminderUiModel>())

    init {
        val reminders = reminderUseCases.getAllRemindersUseCase.invoke()
        reminders.onEach { reminders ->
            this.reminders.update { reminders.fastMap(Reminder::toUiModel) }
        }.launchIn(viewModelScope)
    }

}