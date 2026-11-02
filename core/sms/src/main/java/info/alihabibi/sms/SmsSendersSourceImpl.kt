package info.alihabibi.sms

import android.content.Context
import android.provider.Telephony
import info.alihabibi.domain.local.repositories.SmsSendersSourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SmsSendersSourceImpl(private val context: Context) : SmsSendersSourceRepository {

    override suspend fun getRecentSmsSenders(): Map<String, String> = withContext(Dispatchers.IO) {
        val smsSenders = linkedMapOf<String, String>()
        try {
            context.contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.BODY),
                "${Telephony.Sms.TYPE} = ?",
                arrayOf(Telephony.Sms.MESSAGE_TYPE_INBOX.toString()),
                "${Telephony.Sms.DATE} DESC"
            )?.use { cursor ->
                val addressIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
                val bodyIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)
                var scannedRows = 0
                while (cursor.moveToNext() && smsSenders.size < 30 && scannedRows++ < 200) {
                    val address = cursor.getString(addressIndex) ?: continue
                    // Rows are sorted newest-first, so the first hit per sender is its latest SMS body
                    if (address !in smsSenders)
                        smsSenders[address] = cursor.getString(bodyIndex).orEmpty()
                }
            }
        } catch (_: SecurityException) {
            // READ_SMS revoked between the permission check and the query
            return@withContext emptyMap()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            return@withContext emptyMap()
        }
        smsSenders
    }

}