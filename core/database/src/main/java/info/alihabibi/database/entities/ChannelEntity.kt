package info.alihabibi.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.alihabibi.domain.local.keys.Keys
import info.alihabibi.domain.models.channel.ChannelIcon

@Entity(tableName = Keys.CHANNELS_TABLE_NAME)
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val channelName: String,
    val channelBalance: String,
    val isBankCardChannel: Boolean,
    val cardNumber: String?,
    val icon: ChannelIcon, // @TypeConverter -> ChannelIconConverter
    val bankName: String?
)