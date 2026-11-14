package info.alihabibi.model.mapper

import info.alihabibi.domain.models.AnnouncementType
import info.alihabibi.domain.models.announcement.Announcement
import info.alihabibi.model.ui_model.announcement.AnnouncementTypeOptionUiModel
import info.alihabibi.model.ui_model.announcement.AnnouncementUiModel

fun Announcement.toUiModel(): AnnouncementUiModel =
    AnnouncementUiModel(
        id = id,
        title = title,
        description = description,
        announcementType = announcementType.toUiOption(),
        transaction = transaction?.toUiModel()
    )

fun AnnouncementUiModel.toDomain(): Announcement =
    Announcement(
        id = id,
        title = title,
        description = description,
        announcementType = announcementType.toDomain(),
        transaction = transaction?.toDomain()
    )

private fun AnnouncementType.toUiOption(): AnnouncementTypeOptionUiModel = when (this) {
    AnnouncementType.IN_APP -> AnnouncementTypeOptionUiModel.IN_APP
    AnnouncementType.BANK_SMS -> AnnouncementTypeOptionUiModel.BANK_SMS
}

private fun AnnouncementTypeOptionUiModel.toDomain(): AnnouncementType = when (this) {
    AnnouncementTypeOptionUiModel.IN_APP -> AnnouncementType.IN_APP
    AnnouncementTypeOptionUiModel.BANK_SMS -> AnnouncementType.BANK_SMS
}
