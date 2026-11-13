package info.alihabibi.domain.local.usecases.database.announcement.usecase

import info.alihabibi.domain.local.usecases.database.announcement.GetAnnouncementByIdUseCase
import info.alihabibi.domain.local.usecases.database.announcement.SaveAnnouncementUseCase

data class AnnouncementUseCases(
    val saveAnnouncementUseCase: SaveAnnouncementUseCase,
    val getAnnouncementByIdUseCase: GetAnnouncementByIdUseCase
)
