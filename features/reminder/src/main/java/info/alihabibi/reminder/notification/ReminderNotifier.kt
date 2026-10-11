package info.alihabibi.reminder.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import info.alihabibi.designsystem.R
import info.alihabibi.designsystem.theme.Primary

internal class ReminderNotifier(private val context: Context) {

    private companion object {
        const val CHANNEL_ID = "reminders"
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun pushNotification(reminderId: Long, text: String) {
        if (!notificationManager.areNotificationsEnabled()) return

        buildNotificationChannel()
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(context.getString(R.string.chortkeh_reminder))
            .setContentText(text)
            .setCategory(Notification.CATEGORY_REMINDER)
            .setContentIntent(buildContentIntent())
            .setAutoCancel(true)
            .build()
        notificationManager.notify("reminder_$reminderId", 0, notification)
    }

    private fun buildNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.reminder_notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.reminder_notification_channel_description)
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildContentIntent(): PendingIntent? {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

}