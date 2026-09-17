package info.alihabibi.database.converters

import androidx.room.TypeConverter
import info.alihabibi.domain.models.transaction.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)

}