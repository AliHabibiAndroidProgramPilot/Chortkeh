package info.alihabibi.reminder.scheduler.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.ui.util.fastFilter
import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.reminder.usecase.ReminderUseCases
import info.alihabibi.reminder.scheduler.ReminderScheduler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReminderRescheduleReceiver : BroadcastReceiver(), KoinComponent {

    private val reminderUseCases: ReminderUseCases by inject()
    private val reminderScheduler: ReminderScheduler by inject()
    private val applicationScope: ApplicationScope by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return
        if (
            (action != Intent.ACTION_BOOT_COMPLETED) &&
            (action != Intent.ACTION_MY_PACKAGE_REPLACED) &&
            (action != AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED)
        ) return

        val currentTime = System.currentTimeMillis()
        val pendingResult = goAsync()
        applicationScope.launch {
            try {
                reminderUseCases.getAllRemindersUseCase().first()
                    .fastFilter { reminder -> reminder.isEnabled }
                    .forEach { reminder ->
                        if (reminder.triggerAtMillis > currentTime)
                            reminderScheduler.schedule(reminder.id, reminder.triggerAtMillis)
                        else
                            reminderUseCases.updateReminderIsEnabledUseCase(reminder.id, false)
                    }
            } finally {
                pendingResult.finish()
            }
        }
    }

}
