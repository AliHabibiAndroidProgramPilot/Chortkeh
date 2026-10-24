package info.alihabibi.new_transaction.navigation

import android.os.Bundle
import info.alihabibi.domain.Keys

data class NewTransactionPrefill(
    val transactionType: String,
    val amount: Long,
    val channelId: Long,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val dayOfWeekName: String,
    val hour: Int,
    val minute: Int
)

fun Bundle.toNewTransactionPrefill(): NewTransactionPrefill? {
    if (!containsKey(Keys.EXTRA_ID_TRANSACTION_TYPE)) return null
    return NewTransactionPrefill(
        transactionType = getString(Keys.EXTRA_ID_TRANSACTION_TYPE).orEmpty(),
        amount = getLong(Keys.EXTRA_ID_TRANSACTION_AMOUNT),
        channelId = getLong(Keys.EXTRA_ID_TRANSACTION_CHANNEL_ID),
        year = getInt(Keys.EXTRA_ID_TRANSACTION_YEAR),
        month = getInt(Keys.EXTRA_ID_TRANSACTION_MONTH),
        dayOfMonth = getInt(Keys.EXTRA_ID_TRANSACTION_DAY_OF_MONTH),
        dayOfWeekName = getString(Keys.EXTRA_ID_TRANSACTION_DAY_OF_WEEK_NAME).orEmpty(),
        hour = getInt(Keys.EXTRA_ID_TRANSACTION_HOUR),
        minute = getInt(Keys.EXTRA_ID_TRANSACTION_MINUTE),
    )
}
