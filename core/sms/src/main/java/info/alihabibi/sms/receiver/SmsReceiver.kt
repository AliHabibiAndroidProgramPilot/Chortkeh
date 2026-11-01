package info.alihabibi.sms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.Keys
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.models.transaction.TransactionType
import info.alihabibi.model.data.TransactionData
import info.alihabibi.sms.notification.SmsNotifier
import info.alihabibi.sms.parser.SmsMatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SmsReceiver : BroadcastReceiver(), KoinComponent {

    private val channelsUseCases: ChannelUseCases by inject()
    private val applicationScope: ApplicationScope by inject()
    private val smsNotifier: SmsNotifier by inject()

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                handleIncomingSms(messages)
            }
            SmsNotifier.ACTION_DENY_TRANSACTION -> {
                val notificationId = intent.getIntExtra(Keys.EXTRA_NOTIFICATION_ID, -1)
                smsNotifier.cancelNotification(notificationId)
            }
            else -> return
        }
    }

    private fun handleIncomingSms(messages: Array<SmsMessage?>?) {
        if (messages.isNullOrEmpty()) return

        val pendingResult = goAsync()
        applicationScope.launch {
            try {
                channelsUseCases.getChannelsUseCase.invoke().firstOrNull()?.let { channels ->
                    messages.forEach { message ->
                        val sender = message?.originatingAddress
                        // Find channel that matches the incoming SMS sender
                        val matchedChannel = channels.find { channel ->
                            SmsMatcher.matches(channel.smsSource, sender)
                        }
                        val transactionData = TransactionData(
                            TransactionType.OUTCOME,
                            568_888,
                            matchedChannel?.id ?: -1,
                            1406,
                            6,
                            17,
                            "یکشنبه",
                            18,
                            30
                        )

                        if (matchedChannel != null)
                            smsNotifier.pushNotification(
                                channelName = matchedChannel.channelName,
                                transactionData = transactionData
                            )
                    }
                } ?: return@launch
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}
