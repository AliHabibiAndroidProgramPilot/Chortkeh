package info.alihabibi.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import info.alihabibi.domain.Keys
import info.alihabibi.domain.models.transaction.TransactionType

@Entity(
    tableName = Keys.TRANSACTION_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["id"],
            childColumns = ["transactionChannelId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["transactionCategoryId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("transactionChannelId"), Index("transactionCategoryId")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val transactionType: TransactionType, // @TypeConverter -> TransactionTypeConverter
    val amount: Long,
    val transactionChannelId: Long?,
    val transactionCategoryId: Long?,
    val date: String,
    val time: String
)

data class DetailedTransaction(
    @Embedded val transaction: TransactionEntity,
    @Relation(parentColumn = "transactionChannelId", entityColumn = "id") val channel: ChannelEntity,
    @Relation(parentColumn = "transactionCategoryId", entityColumn = "id") val category: CategoryEntity
)
