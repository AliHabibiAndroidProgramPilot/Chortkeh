package info.alihabibi.sms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import info.alihabibi.common.ApplicationScope
import info.alihabibi.common.PersianDateFormatter
import info.alihabibi.designsystem.R
import info.alihabibi.domain.local.usecases.database.announcement.usecase.AnnouncementUseCases
import info.alihabibi.domain.local.usecases.database.channel.usecase.ChannelUseCases
import info.alihabibi.domain.local.usecases.datastore.usecase.DatastoreUseCases
import info.alihabibi.domain.models.AnnouncementType
import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.domain.models.channel.Channel
import info.alihabibi.domain.models.transaction.Transaction
import info.alihabibi.domain.models.transaction.TransactionType
import info.alihabibi.model.data.TransactionData
import info.alihabibi.sms.notification.SmsNotifier
import info.alihabibi.sms.parser.SmsMatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Locale

class SmsReceiver : BroadcastReceiver(), KoinComponent {

    private val channelsUseCases: ChannelUseCases by inject()
    private val announcementUseCase: AnnouncementUseCases by inject()
    private val datastoreUseCases: DatastoreUseCases by inject()
    private val applicationScope: ApplicationScope by inject()
    private val smsNotifier: SmsNotifier by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            handleIncomingSms(messages, context.applicationContext)
        }
    }

    private fun handleIncomingSms(messages: Array<SmsMessage?>?, context: Context) {
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
                    transactionData?.let { transactionData ->
                        val announcementId = updateAnnouncement(transactionData, matchedChannel, context)
                        datastoreUseCases.saveHomeAnnouncementRedBadge.invoke(true)
                        smsNotifier.pushNotification(
                            channelName = matchedChannel.channelName,
                            transactionData = transactionData,
                            announcementId = announcementId
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }

    private suspend fun updateAnnouncement(transactionData: TransactionData, channel: Channel, context: Context): Long {
        val typeText: String = when(transactionData.transactionType) {
            TransactionType.INCOME -> context.getString(R.string.income_announcement_title, transactionData.amount)
            TransactionType.OUTCOME -> context.getString(R.string.outcome_announcement_title, transactionData.amount)
        }
        val description = PersianDateFormatter.format(
            transactionData.year,
            transactionData.month,
            transactionData.dayOfWeekName,
            transactionData.dayOfMonth,
            transactionData.hour,
            transactionData.minute
        )
        return announcementUseCase.saveAnnouncementUseCase.invoke(
            Announcement(
                title = typeText,
                description = description,
                announcementType = AnnouncementType.BANK_SMS,
                transaction = Transaction(
                    transactionType = transactionData.transactionType,
                    amount = transactionData.amount ?: 0L,
                    channel = channel,
                    category = null,
                    year = transactionData.year,
                    month = transactionData.month,
                    day = transactionData.dayOfMonth,
                    dayOfWeekName = transactionData.dayOfWeekName,
                    time = String.format(Locale.US, "%02d : %02d", transactionData.hour, transactionData.minute)
                )
            )
        )
    }

}
