package info.alihabibi.reminder

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.common.Utils
import info.alihabibi.common.Utils.loog
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import info.alihabibi.model.mapper.toDomain
import info.alihabibi.model.ui_model.reminder.ReminderUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val reminderUseCases: ReminderUseCases
) : ViewModel() {

    val uiState: StateFlow<ReminderUiState>
        field = MutableStateFlow(ReminderUiState())

    val reminders: StateFlow<List<ReminderUiModel>>
        field = MutableStateFlow(emptyList<ReminderUiModel>())

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

    fun onEvent(event: RemindersUiIntent) {
        when (event) {

            is RemindersUiIntent.ChangeReminderName -> changeReminderName(event.name)

            is RemindersUiIntent.ChangeReminderDate -> changeReminderDate(
                event.year,
                event.month,
                event.day,
                event.triggerTimeStamp
            )

            is RemindersUiIntent.ChangeReminderTime -> changeReminderTime(event.hour, event.minute)

            is RemindersUiIntent.SaveReminder -> saveReminder()

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
            if (hour != null && minute != null) "$hour : $minute" else ""
        uiState.update {
            it.copy(
                reminderHour = hour,
                reminderMinute = minute,
                formattedReminderTime = formattedTime
            )
        }
    }

    private fun saveReminder() {
        viewModelScope.launch {
            val state = uiState.value
            if (state.reminderTitle.isBlank() || state.reminderMinute == null || state.reminderHour == null || state.triggerTimeStamp == null)
                return@launch
            val time = "${state.reminderHour} : ${state.reminderMinute}"
            val reminder = ReminderUiModel(
                title = state.reminderTitle,
                isEnabled = true,
                triggerTimeStamp = Utils.mergeTimeIntoEpochMillis(state.triggerTimeStamp, time),
                isPassed = null,
                year = state.reminderYear,
                month = state.reminderMonth,
                day = state.reminderDay.first,
                dayOfWeekName = state.reminderDay.second,
                time = time
            ).toDomain()
            reminderUseCases.saveReminderUseCases.invoke(reminder)
        }
    }

}

sealed interface RemindersUiIntent {

    data class ChangeReminderName(val name: String) : RemindersUiIntent

    data class ChangeReminderDate(
        val year: Int,
        val month: Int,
        val day: Pair<Int, String>,
        val triggerTimeStamp: Long
    ) : RemindersUiIntent

    data class ChangeReminderTime(val hour: Int?, val minute: Int?) : RemindersUiIntent

    data object SaveReminder : RemindersUiIntent

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
    val triggerTimeStamp: Long? = null
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