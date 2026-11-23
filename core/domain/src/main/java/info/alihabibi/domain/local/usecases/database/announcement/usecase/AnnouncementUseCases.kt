package info.alihabibi.domain.local.usecases.database.announcement.usecase

import info.alihabibi.domain.local.usecases.database.announcement.DeleteAnnouncementByIdUseCase
import info.alihabibi.domain.local.usecases.database.announcement.GetAllSmsAnnouncements
import info.alihabibi.domain.local.usecases.database.announcement.GetTransactionByAnnouncementIdUseCase
import info.alihabibi.domain.local.usecases.database.announcement.HasSmsAnnouncementsUseCase
import info.alihabibi.domain.local.usecases.database.announcement.SaveAnnouncementUseCase

data class AnnouncementUseCases(
    val saveAnnouncementUseCase: SaveAnnouncementUseCase,
    val getTransactionByAnnouncementUseCases: GetTransactionByAnnouncementIdUseCase,
    val getAllSmsAnnouncements: GetAllSmsAnnouncements,
    val hasSmsAnnouncementsUseCase: HasSmsAnnouncementsUseCase,
    val deleteAnnouncementByIdUseCase: DeleteAnnouncementByIdUseCase
)
