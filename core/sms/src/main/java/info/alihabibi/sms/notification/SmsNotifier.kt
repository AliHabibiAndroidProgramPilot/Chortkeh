package info.alihabibi.sms.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.core.net.toUri
import info.alihabibi.designsystem.R
import info.alihabibi.domain.Keys
import info.alihabibi.sms.parser.TransactionData
import info.alihabibi.sms.receiver.SmsReceiver

internal class SmsNotifier(private val context: Context) {

    internal companion object {
        private const val NOTIFICATION_CHANNEL_ID = "sms_detection"
        private const val ACTION_ACCEPT_TRANSACTION = "info.alihabibi.ACTION_ACCEPT_TRANSACTION"
        const val NEW_TRANSACTION_URI = "chortkeh://new_transaction"
        const val INTENT_EXTRA_ID = "notification_id"
        const val ACTION_DENY_TRANSACTION = "info.alihabibi.ACTION_DENY_TRANSACTION"
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    internal fun pushNotification(
        notificationId: Int = System.currentTimeMillis().toInt(),
        channelName: String,
        transactionData: TransactionData
    ) {
        if (!notificationManager.areNotificationsEnabled()) return

        val title = context.getString(R.string.sms_detected_title, channelName)
        val text = context.getString(R.string.sms_detected_description, channelName)

        buildNotificationChannel()
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setColor(context.getColor(R.color.primary))
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(Notification.BigTextStyle().bigText(text))
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setContentIntent(buildContentIntent(notificationId, transactionData))
            .setAutoCancel(true)
            .addAction(buildAcceptAction(notificationId, transactionData))
            .addAction(buildDenyAction(notificationId))
            .build()

        notificationManager.notify(notificationId, notification)
    }

    internal fun cancelNotification(notificationId: Int) = notificationManager.cancel(notificationId)

    private fun buildNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
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

    private fun buildContentIntent(notificationId: Int, transactionData: TransactionData): PendingIntent? {
        val intent = Intent(Intent.ACTION_VIEW, NEW_TRANSACTION_URI.toUri()).apply {
            this.setPackage(context.packageName)
            putExtra(Keys.EXTRA_ID_TRANSACTION_TYPE, transactionData.transactionType.name)
            putExtra(Keys.EXTRA_ID_TRANSACTION_AMOUNT, transactionData.amount)
            putExtra(Keys.EXTRA_ID_TRANSACTION_CHANNEL_ID, transactionData.channelId)
            putExtra(Keys.EXTRA_ID_TRANSACTION_YEAR, transactionData.year)
            putExtra(Keys.EXTRA_ID_TRANSACTION_MONTH, transactionData.month)
            putExtra(Keys.EXTRA_ID_TRANSACTION_DAY_OF_MONTH, transactionData.dayOfMonth)
            putExtra(Keys.EXTRA_ID_TRANSACTION_DAY_OF_WEEK_NAME, transactionData.dayOfWeekName)
            putExtra(Keys.EXTRA_ID_TRANSACTION_HOUR, transactionData.hour)
            putExtra(Keys.EXTRA_ID_TRANSACTION_MINUTE, transactionData.minute)
        }
         return PendingIntent.getActivity(
            context,
            // unique request code per notification + action, otherwise PendingIntents collide
            notificationId * 21 + ACTION_ACCEPT_TRANSACTION.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun buildDenyAction(notificationId: Int): Notification.Action {
        val intent = Intent(context, SmsReceiver::class.java).apply {
            this.setAction(ACTION_DENY_TRANSACTION)
            putExtra(INTENT_EXTRA_ID, notificationId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            // unique request code per notification + action, otherwise PendingIntents collide
            notificationId * 21 + ACTION_DENY_TRANSACTION.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return Notification.Action.Builder(null, context.getString(R.string.dismiss), pendingIntent).build()
    }

    private fun buildAcceptAction(notificationId: Int, transactionData: TransactionData): Notification.Action {
        val intent = Intent(Intent.ACTION_VIEW, NEW_TRANSACTION_URI.toUri()).apply {
            this.setPackage(context.packageName)
            putExtra(Keys.EXTRA_ID_TRANSACTION_TYPE, transactionData.transactionType.name)
            putExtra(Keys.EXTRA_ID_TRANSACTION_AMOUNT, transactionData.amount)
            putExtra(Keys.EXTRA_ID_TRANSACTION_CHANNEL_ID, transactionData.channelId)
            putExtra(Keys.EXTRA_ID_TRANSACTION_YEAR, transactionData.year)
            putExtra(Keys.EXTRA_ID_TRANSACTION_MONTH, transactionData.month)
            putExtra(Keys.EXTRA_ID_TRANSACTION_DAY_OF_MONTH, transactionData.dayOfMonth)
            putExtra(Keys.EXTRA_ID_TRANSACTION_DAY_OF_WEEK_NAME, transactionData.dayOfWeekName)
            putExtra(Keys.EXTRA_ID_TRANSACTION_HOUR, transactionData.hour)
            putExtra(Keys.EXTRA_ID_TRANSACTION_MINUTE, transactionData.minute)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            // unique request code per notification + action, otherwise PendingIntents collide
            notificationId * 21 + ACTION_ACCEPT_TRANSACTION.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return Notification.Action.Builder(null, context.getString(R.string.register_transaction), pendingIntent).build()
    }
}
