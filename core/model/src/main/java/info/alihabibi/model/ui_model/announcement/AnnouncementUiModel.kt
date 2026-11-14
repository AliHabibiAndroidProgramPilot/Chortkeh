package info.alihabibi.model.ui_model.announcement

import info.alihabibi.model.ui_model.transaction.TransactionUiModel

data class AnnouncementUiModel(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val announcementType: AnnouncementTypeOptionUiModel,
    val transaction: TransactionUiModel? = null
)