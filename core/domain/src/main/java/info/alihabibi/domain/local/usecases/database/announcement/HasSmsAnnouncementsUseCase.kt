package info.alihabibi.domain.local.usecases.database.announcement

import info.alihabibi.domain.local.repositories.AnnouncementRepository

class HasSmsAnnouncementsUseCase(
    private val repository: AnnouncementRepository
) {

    suspend operator fun invoke(): Boolean = repository.hasSmsAnnouncements()

}
