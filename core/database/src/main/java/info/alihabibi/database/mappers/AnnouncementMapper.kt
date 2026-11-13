package info.alihabibi.database.mappers

import info.alihabibi.database.entities.AnnouncementEntity
import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.database.entities.TransactionEntity
import info.alihabibi.domain.models.transaction.Transaction

fun AnnouncementEntity.asExternalModel(): Announcement = Announcement(
    id = id,
    title = title,
    description = description,
    announcementType = announcementType,
    transaction = transaction?.let {
        Transaction(
            id = it.id,
            transactionType = it.transactionType,
            amount = it.amount,
            channel = null,
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
