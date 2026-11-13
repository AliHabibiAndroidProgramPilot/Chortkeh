package info.alihabibi.database.converters

import androidx.room.TypeConverter
import info.alihabibi.domain.models.AnnouncementType

class AnnouncementTypeConverter {

    @TypeConverter
    fun fromAnnouncementType(type: AnnouncementType): String = type.name

    @TypeConverter
    fun toAnnouncementType(value: String): AnnouncementType =
        AnnouncementType.entries.find { it.name == value } ?: AnnouncementType.IN_APP

}