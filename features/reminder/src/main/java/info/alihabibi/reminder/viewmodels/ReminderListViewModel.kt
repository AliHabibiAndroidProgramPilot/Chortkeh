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
import kotlinx.coroutines.launch

class ReminderListViewModel(private val reminderUseCases: ReminderUseCases) : ViewModel() {

    val reminders: StateFlow<List<ReminderUiModel>>
        field = MutableStateFlow(emptyList<ReminderUiModel>())

    init {
        val reminders = reminderUseCases.getAllRemindersUseCase.invoke()
        reminders.onEach { reminders ->
            this.reminders.update { reminders.fastMap(Reminder::toUiModel) }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ReminderListUiIntent) {
        when(event) {

            is ReminderListUiIntent.ReminderEnabledChanged -> reminderChangeEnable(event.id, event.value)

        }
    }

    private fun reminderChangeEnable(id: Long, value: Boolean) {
        viewModelScope.launch {
            reminderUseCases.updateReminderIsEnabledUseCase.invoke(id, value)
        }
    }

}

sealed interface ReminderListUiIntent {

    data class ReminderEnabledChanged(val id: Long, val value: Boolean) : ReminderListUiIntent

}