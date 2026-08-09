package info.alihabibi.database.converters

import androidx.room.TypeConverter
import info.alihabibi.domain.models.channel.ChannelIcon

class ChannelIconConverter {

    @TypeConverter
    fun fromChannelIcon(icon: ChannelIcon): String = icon.name

    @TypeConverter
    fun toCategoryIcon(value: String): ChannelIcon = ChannelIcon.valueOf(value)

}