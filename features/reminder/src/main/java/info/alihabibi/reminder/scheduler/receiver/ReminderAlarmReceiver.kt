package info.alihabibi.reminder.scheduler.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import info.alihabibi.reminder.notification.ReminderNotifier
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReminderAlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val reminderUseCases: ReminderUseCases by inject()
    private val notifier: ReminderNotifier by inject()
    private val applicationScope: ApplicationScope by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val reminderId = intent?.data?.lastPathSegment?.toLongOrNull() ?: return

        val pendingResult = goAsync()
        applicationScope.launch {
            try {
                val reminder = reminderUseCases.getReminderByIdUseCase.invoke(reminderId)
                if (!reminder.isEnabled) return@launch
                notifier.pushNotification(reminderId, reminder.title)
                reminderUseCases.updateReminderIsEnabledUseCase.invoke(reminderId, false)
            } finally {
                pendingResult.finish()
            }
        }
    }

}