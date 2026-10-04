package info.alihabibi.reminder

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import info.alihabibi.model.ui_model.reminder.ReminderUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReminderViewModel : ViewModel(){

    val uiState: StateFlow<ReminderUiState>
        field = MutableStateFlow(ReminderUiState())

    val reminders: StateFlow<List<ReminderUiModel>>
        field = MutableStateFlow(emptyList<ReminderUiModel>())

}

@Immutable
data class ReminderUiState(
    val reminderTitle: String = ""
)