package info.alihabibi.domain.local.usecases.database.announcement

import info.alihabibi.domain.local.repositories.AnnouncementRepository

class DeleteAnnouncementByIdUseCase(
    private val repository: AnnouncementRepository
) {

    suspend operator fun invoke(id: Long) = repository.deleteAnnouncementById(id)

}