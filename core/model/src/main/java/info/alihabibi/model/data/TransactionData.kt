package info.alihabibi.model.data

import android.os.Parcelable
import info.alihabibi.domain.models.transaction.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionData(
    val transactionType: TransactionType,
    val amount: Long,
    val channelId: Long,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val dayOfWeekName: String,
    val hour: Int,
    val minute: Int
) : Parcelable
