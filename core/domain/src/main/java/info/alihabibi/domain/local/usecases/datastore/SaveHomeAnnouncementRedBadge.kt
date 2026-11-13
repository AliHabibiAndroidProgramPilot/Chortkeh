package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository

class SaveHomeAnnouncementRedBadge(
    private val repository: DatastoreRepository
) {

    suspend operator fun invoke(value: Boolean) = repository.saveHomeAnnouncementRedBadge(value)

}