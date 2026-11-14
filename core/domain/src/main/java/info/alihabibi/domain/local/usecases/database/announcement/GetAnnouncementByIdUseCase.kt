package info.alihabibi.domain.local.usecases.database.announcement

import info.alihabibi.domain.local.repositories.AnnouncementRepository
import info.alihabibi.domain.models.announcement.Announcement
import kotlinx.coroutines.flow.Flow

class GetAnnouncementByIdUseCase(
    private val repository: AnnouncementRepository
) {
    operator fun invoke(id: Long): Flow<Announcement?> = repository.getAnnouncementById(id)
}
