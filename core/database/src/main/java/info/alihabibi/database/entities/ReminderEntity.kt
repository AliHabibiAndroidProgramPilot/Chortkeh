package info.alihabibi.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import info.alihabibi.domain.Keys

@Entity(
    tableName = Keys.REMINDER_TABLE_NAME,
    indices = [Index("triggerAtTimeStamp")]
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val isEnabled: Boolean,
    val triggerAtTimeStamp: Long,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeekName: String,
    val time: String
)
