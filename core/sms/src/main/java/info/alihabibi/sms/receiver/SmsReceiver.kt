package info.alihabibi.sms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import info.alihabibi.common.ApplicationScope
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
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
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            handleIncomingSms(messages)
        }
    }

    private fun handleIncomingSms(messages: Array<SmsMessage?>?) {
        if (messages.isNullOrEmpty()) return

        val sms = messages.firstOrNull() ?: return
        val fullSmsRawText = messages
            .filterNotNull()
            .joinToString("") { it.messageBody.orEmpty() }

        val pendingResult = goAsync()
        applicationScope.launch {
            try {
                val channels = channelsUseCases.getChannelsUseCase.invoke().firstOrNull()
                val matchedChannel = channels?.find { channel ->
                    SmsMatcher.matches(channel.smsSource, sms.originatingAddress)
                }
                if (matchedChannel != null) {
                    val transactionData = SmsMatcher.createTransactionData(
                        fullSmsRawText,
                        matchedChannel.id,
                        matchedChannel.bankName,
                        sms.timestampMillis
                    )
                    smsNotifier.pushNotification(channelName = matchedChannel.channelName, transactionData = transactionData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}
