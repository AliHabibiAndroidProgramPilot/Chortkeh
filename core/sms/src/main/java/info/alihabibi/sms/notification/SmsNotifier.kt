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
import info.alihabibi.common.Utils
import info.alihabibi.common_android.NotificationCanceler
import info.alihabibi.designsystem.R
import info.alihabibi.domain.Keys
import info.alihabibi.model.data.TransactionData
import info.alihabibi.sms.receiver.SmsNotificationReceiver
import java.util.Locale

internal class SmsNotifier(private val context: Context) : NotificationCanceler {

    internal companion object {
        private const val NOTIFICATION_CHANNEL_ID = "sms_detection"
        private const val ACTION_ACCEPT_TRANSACTION = "info.alihabibi.ACTION_ACCEPT_TRANSACTION"
        const val NEW_TRANSACTION_URI = "chortkeh://new_transaction"
        const val ACTION_DENY_TRANSACTION = "info.alihabibi.ACTION_DENY_TRANSACTION"
        const val MAIN_ACTIVITY_DESTINATION = "info.alihabibi.chortkeh.activity.MainActivity"
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    internal fun pushNotification(
        notificationId: Int = System.currentTimeMillis().toInt(),
        channelName: String,
        transactionData: TransactionData
    ) {
        if (!notificationManager.areNotificationsEnabled()) return

        val title = context.getString(R.string.sms_detected_title, channelName)
        val text = buildNotificationText(
            transactionData.amount ?: 0,
            transactionData.dayOfWeekName,
            transactionData.hour,
            transactionData.minute,
            channelName
        )

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

    override fun cancel(notificationId: Int) = notificationManager.cancel(notificationId)

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
            setClassName(context.packageName, MAIN_ACTIVITY_DESTINATION)
            setPackage(context.packageName)
            putExtra(Keys.EXTRA_ID_TRANSACTION_DATA, transactionData)
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
        val intent = Intent(context, SmsNotificationReceiver::class.java).apply {
            setAction(ACTION_DENY_TRANSACTION)
            putExtra(Keys.EXTRA_NOTIFICATION_ID, notificationId)
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
            setClassName(context.packageName, MAIN_ACTIVITY_DESTINATION)
            setPackage(context.packageName)
            putExtra(Keys.EXTRA_NOTIFICATION_ID, notificationId)
            putExtra(Keys.EXTRA_ID_TRANSACTION_DATA, transactionData)
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

    private fun buildNotificationText(
        amount: Long?,
        dayOfWeekName: String?,
        hour: Int?,
        minute: Int?,
        channelName: String
    ): String {
        val time = if (hour != null && minute != null)
            String.format(Locale.US, "%02d:%02d", hour, minute) else null
        val date = listOfNotNull(dayOfWeekName?.takeIf(String::isNotBlank), time)
            .takeIf { it.isNotEmpty() }
            ?.joinToString("، ")

        return buildList {
            amount?.let {
                add(context.getString(R.string.sms_detected_amount, Utils.decimalFormatterPattern.format(it)))
            }
            date?.let { add(context.getString(R.string.sms_detected_date, it)) }
            add(context.getString(R.string.sms_detected_channel, channelName))
            add(context.getString(R.string.sms_detected_question))
        }.joinToString("\n")
    }
}
