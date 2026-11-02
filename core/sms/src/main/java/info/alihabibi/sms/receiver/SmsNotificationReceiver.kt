package info.alihabibi.sms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import info.alihabibi.domain.Keys
import info.alihabibi.sms.notification.SmsNotifier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SmsNotificationReceiver : BroadcastReceiver(), KoinComponent {

    private val smsNotifier: SmsNotifier by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SmsNotifier.ACTION_DENY_TRANSACTION) {
            val notificationId = intent.getIntExtra(Keys.EXTRA_NOTIFICATION_ID, -1)
            smsNotifier.cancelNotification(notificationId)
        }
    }
}
