package info.alihabibi.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import info.alihabibi.domain.Keys
import info.alihabibi.domain.models.AnnouncementType

@Entity(
    tableName = Keys.ANNOUNCEMENT_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["id"],
            childColumns = ["tx_transactionChannelId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("tx_transactionChannelId")
    ]
)
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String,
    val announcementType: AnnouncementType,  // @TypeConverter -> AnnouncementTypeConverter
    @Embedded(prefix = "tx_") val transaction: TransactionEntity? = null
)
