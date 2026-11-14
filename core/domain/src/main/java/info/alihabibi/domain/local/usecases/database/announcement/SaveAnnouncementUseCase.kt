package info.alihabibi.domain.local.usecases.database.announcement

import info.alihabibi.domain.local.repositories.AnnouncementRepository
import info.alihabibi.domain.models.announcement.Announcement

class SaveAnnouncementUseCase(
    private val repository: AnnouncementRepository
) {
    suspend operator fun invoke(announcement: Announcement): Long = repository.saveAnnouncement(announcement)
}
