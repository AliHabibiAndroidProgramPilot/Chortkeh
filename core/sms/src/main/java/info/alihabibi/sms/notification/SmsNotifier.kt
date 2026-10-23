package info.alihabibi.sms.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.annotation.StringRes
import info.alihabibi.designsystem.R
import info.alihabibi.sms.receiver.SmsReceiver

internal class SmsNotifier(private val context: Context) {

    internal companion object {
        private const val CHANNEL_ID = "sms_detection"
        const val ACTIONS_INTENT_EXTRA_ID = "notification_id"
        const val ACTION_DENY_TRANSACTION = "info.alihabibi.ACTION_DENY_TRANSACTION"
        const val ACTION_ACCEPT_TRANSACTION = "info.alihabibi.ACTION_ACCEPT_TRANSACTION"
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    internal fun pushNotification(notificationId: Int = System.currentTimeMillis().toInt(), channelName: String) {
        if (!notificationManager.areNotificationsEnabled()) return

        val title = context.getString(R.string.sms_detected_title, channelName)
        val text = context.getString(R.string.sms_detected_description, channelName)

        buildNotificationChannel()
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setColor(context.getColor(R.color.primary))
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(Notification.BigTextStyle().bigText(text))
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setContentIntent(buildContentIntent())
            .setAutoCancel(true)
            .addAction(buildAction(notificationId, ACTION_ACCEPT_TRANSACTION, R.string.register_transaction))
            .addAction(buildAction(notificationId, ACTION_DENY_TRANSACTION, R.string.dismiss))
            .build()

        notificationManager.notify(notificationId, notification)
    }

    internal fun cancelNotification(notificationId: Int) = notificationManager.cancel(notificationId)

    private fun buildNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.sms_detection_notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.sms_detection_notification_channel_description)
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
        val launchIntent =
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            } ?: return null

        return PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun buildAction(
        notificationId: Int,
        action: String,
        @StringRes title: Int
    ): Notification.Action {
        val intent = Intent(context, SmsReceiver::class.java).apply {
            this.action = action
            putExtra(ACTIONS_INTENT_EXTRA_ID, notificationId)
        }
        val pending = PendingIntent.getBroadcast(
            context,
            // unique request code per notification + action, otherwise PendingIntents collide
            notificationId * 31 + action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return Notification.Action.Builder(null, context.getString(title), pending).build()
    }
}
