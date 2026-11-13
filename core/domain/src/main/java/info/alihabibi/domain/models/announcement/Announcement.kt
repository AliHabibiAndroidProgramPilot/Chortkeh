package info.alihabibi.domain.models.announcement

import info.alihabibi.domain.models.AnnouncementType
import info.alihabibi.domain.models.transaction.Transaction

data class Announcement(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val announcementType: AnnouncementType,
    val transaction: Transaction? = null
)
