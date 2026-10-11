package info.alihabibi.reminder.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import info.alihabibi.common_android.AndroidUtils
import info.alihabibi.reminder.scheduler.receiver.ReminderAlarmReceiver

internal class AlarmManagerReminderScheduler(private val context: Context) : ReminderScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(reminderId: Long, triggerAtMillis: Long) {
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            data = "chortkeh://reminder/$reminderId".toUri()
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0, // resolve pending intents identity by intent data (each data contains unique reminder id)
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        if (AndroidUtils.canScheduleExactAlarms(context))
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        else
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    override fun cancelReminder(reminderId: Long) {
        val intent = Intent(context, ReminderAlarmReceiver::class.java).apply {
            data = "chortkeh://reminder/$reminderId".toUri()
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ) ?: return
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

}