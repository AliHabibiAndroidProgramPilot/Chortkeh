package info.alihabibi.sms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.sms.SmsMatcher
import info.alihabibi.sms.notification.SmsNotifier
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SmsReceiver : BroadcastReceiver(), KoinComponent {

    private val channelsUseCases: ChannelUseCases by inject()
    private val applicationScope: ApplicationScope by inject()
    private val smsNotifier: SmsNotifier by inject()

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> handleIncomingSms(intent)
            SmsNotifier.ACTION_ACCEPT_TRANSACTION,
            SmsNotifier.ACTION_DENY_TRANSACTION -> handleNotificationAction(intent)
        }

    }

    private fun handleIncomingSms(intent: Intent) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        if (messages.isNullOrEmpty()) return

        val pendingResult = goAsync()

        applicationScope.launch {
            try {
                channelsUseCases.getChannelsUseCase.invoke().firstOrNull()?.let { channels ->
                    for (message in messages) {
                        val sender = message.originatingAddress ?: continue
                        // Find channel that matches the incoming SMS sender
                        val matchingChannel = channels.find { channel ->
                            SmsMatcher.matches(channel.smsSource, sender)
                        }

                        if (matchingChannel != null)
                            smsNotifier.pushNotification(channelName = matchingChannel.channelName)
                    }
                } ?: return@launch
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun handleNotificationAction(intent: Intent) {
        val notificationId = intent.getIntExtra(SmsNotifier.ACTIONS_INTENT_EXTRA_ID, -1)
        val action = intent.action
        val pendingResult = goAsync()

        applicationScope.launch {
            try {
                when (action) {
                    SmsNotifier.ACTION_ACCEPT_TRANSACTION -> {
                        // e.g. channelsUseCases.xxx.invoke(...)
                    }

                    SmsNotifier.ACTION_DENY_TRANSACTION -> {
                        // ...
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (notificationId != -1) smsNotifier.cancelNotification(notificationId)
                pendingResult.finish()
            }
        }
    }
}
