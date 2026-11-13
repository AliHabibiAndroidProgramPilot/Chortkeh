package info.alihabibi.database.mappers

import info.alihabibi.database.entities.AnnouncementEntity
import info.alihabibi.database.entities.DetailedAnnouncement
import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.domain.models.transaction.Transaction

fun DetailedAnnouncement.asExternalModel(): Announcement = Announcement(
    id = announcement.id,
    title = announcement.title,
    description = announcement.description,
    announcementType = announcement.announcementType,
    transaction = announcement.transaction?.let {
        Transaction(
            id = it.id,
            transactionType = it.transactionType,
            amount = it.amount,
            channel = channel?.asExternalModel(),
            category = null,
            year = it.year,
            month = it.month,
            day = it.day,
            dayOfWeekName = it.dayOfWeekName,
            time = it.time
        )
    }
)

fun Announcement.asEntity(): AnnouncementEntity = AnnouncementEntity(
    id = id,
    title = title,
    description = description,
    announcementType = announcementType,
    transaction = transaction?.asEntity()
)
