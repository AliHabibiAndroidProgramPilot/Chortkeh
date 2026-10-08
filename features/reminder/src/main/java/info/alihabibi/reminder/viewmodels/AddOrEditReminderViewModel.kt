package info.alihabibi.reminder.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.common.Utils
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.mapper.toUiModel
import info.alihabibi.model.ui_model.reminder.ReminderUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class AddOrEditReminderViewModel(
    private val reminderUseCases: ReminderUseCases
) : ViewModel() {

    val uiState: StateFlow<ReminderUiState>
        field = MutableStateFlow(ReminderUiState())

    val formattedReminderDate: StateFlow<String> = combine(
        uiState.map { it.reminderYear },
        uiState.map { it.reminderMonth },
        uiState.map { it.reminderDay }
    ) { year, month, day ->
        if (year > 0 && month > 0 && day.first > 0)
            PersianDateFormatter.format(year, month, day.first)
        else ""
    }.stateIn(
        scope = viewModelScope,
        initialValue = "",
        started = SharingStarted.WhileSubscribed(5_000, 10_000)
    )

    fun onEvent(event: AddOrEditReminderUiIntent) {
        when (event) {

            is AddOrEditReminderUiIntent.ChangeReminderName -> changeReminderName(event.name)

            is AddOrEditReminderUiIntent.ChangeReminderDate -> changeReminderDate(
                event.year,
                event.month,
                event.day,
                event.triggerTimeStamp
            )

            is AddOrEditReminderUiIntent.ChangeReminderTime -> changeReminderTime(event.hour, event.minute)

            is AddOrEditReminderUiIntent.FetchEditingReminder -> fetchEditingReminder(event.id)

            is AddOrEditReminderUiIntent.EditReminder -> editReminder(event.id)

            is AddOrEditReminderUiIntent.SaveReminder -> saveReminder()

        }
    }

    private fun changeReminderName(name: String) {
        if (name.length <= 30)
            uiState.update { it.copy(reminderTitle = name) }
    }

    private fun changeReminderDate(
        year: Int,
        month: Int,
        day: Pair<Int, String>,
        triggerTimeStamp: Long
    ) {
        uiState.update {
            it.copy(
                reminderYear = year,
                reminderMonth = month,
                reminderDay = day,
                triggerTimeStamp = triggerTimeStamp
            )
        }
    }

    private fun changeReminderTime(hour: Int?, minute: Int?) {
        val formattedTime =
            if (hour != null && minute != null) String.format(Locale.US,"%02d:%02d", hour, minute) else ""
        uiState.update {
            it.copy(
                reminderHour = hour,
                reminderMinute = minute,
                formattedReminderTime = formattedTime
            )
        }
    }

    private fun fetchEditingReminder(id: Long) {
        viewModelScope.launch {
            val reminder = reminderUseCases.getReminderByIdUseCase.invoke(id).toUiModel()
            val hour = reminder.time.substringBefore(':').toIntOrNull() ?: 0
            val minute = reminder.time.substringAfter(':').toIntOrNull() ?: 0
            val formattedReminderTime = String.format(Locale.US,"%02d:%02d", hour, minute)
            uiState.update {
                it.copy(
                    reminderTitle = reminder.title,
                    reminderHour = hour,
                    reminderMinute = minute,
                    formattedReminderTime = formattedReminderTime,
                    triggerTimeStamp = reminder.triggerTimeStamp,
                    reminderYear = reminder.year,
                    reminderMonth = reminder.month,
                    reminderDay = Pair(reminder.day, reminder.dayOfWeekName)
                )
            }
        }
    }

    private fun editReminder(id: Long) {
        viewModelScope.launch {
            val state = uiState.value
            if (state.reminderTitle.isBlank() || state.formattedReminderTime.isBlank() || state.triggerTimeStamp == null)
                return@launch
            val reminder = ReminderUiModel(
                id = id,
                title = state.reminderTitle,
                isEnabled = true,
                triggerTimeStamp = Utils.mergeTimeIntoEpochMillis(state.triggerTimeStamp, state.formattedReminderTime),
                isPassed = null,
                year = state.reminderYear,
                month = state.reminderMonth,
                day = state.reminderDay.first,
                dayOfWeekName = state.reminderDay.second,
                time = state.formattedReminderTime
            ).toDomain()
            reminderUseCases.updateReminderUseCase.invoke(reminder)
        }
    }

    private fun saveReminder() {
        viewModelScope.launch {
            val state = uiState.value
            if (state.reminderTitle.isBlank() || state.formattedReminderTime.isBlank() || state.triggerTimeStamp == null)
                return@launch
            val reminder = ReminderUiModel(
                title = state.reminderTitle,
                isEnabled = true,
                triggerTimeStamp = Utils.mergeTimeIntoEpochMillis(state.triggerTimeStamp, state.formattedReminderTime),
                isPassed = null,
                year = state.reminderYear,
                month = state.reminderMonth,
                day = state.reminderDay.first,
                dayOfWeekName = state.reminderDay.second,
                time = state.formattedReminderTime
            ).toDomain()
            val id = reminderUseCases.saveReminderUseCases.invoke(reminder)
            uiState.update { it.copy(savedReminderId = id) }
        }
    }

}

sealed interface AddOrEditReminderUiIntent {

    data class ChangeReminderName(val name: String) : AddOrEditReminderUiIntent

    data class ChangeReminderDate(
        val year: Int,
        val month: Int,
        val day: Pair<Int, String>,
        val triggerTimeStamp: Long
    ) : AddOrEditReminderUiIntent

    data class ChangeReminderTime(val hour: Int?, val minute: Int?) : AddOrEditReminderUiIntent

    data class FetchEditingReminder(val id: Long) : AddOrEditReminderUiIntent

    data class EditReminder(val id: Long) : AddOrEditReminderUiIntent

    data object SaveReminder : AddOrEditReminderUiIntent

}

@Immutable
data class ReminderUiState(
    val reminderTitle: String = "",
    val reminderHour: Int? = null,
    val reminderMinute: Int? = null,
    val formattedReminderTime: String = "",
    val reminderYear: Int = 0,
    val reminderMonth: Int = 0,
    val reminderDay: Pair<Int, String> = Pair(0, ""),
    val triggerTimeStamp: Long? = null,
    val savedReminderId: Long? = null
) {
    val isRegisterReminderButtonEnabled: Boolean
        get() {
            return reminderTitle.isNotBlank() &&
                    reminderYear > 0 &&
                    reminderMonth > 0 &&
                    reminderDay.first > 0 &&
                    reminderHour != null &&
                    reminderMinute != null
        }
}