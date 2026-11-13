package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import kotlinx.coroutines.flow.Flow

class GetAnnouncementBadgeEnabled(
    private val repository: DatastoreRepository
) {

    operator fun invoke(): Flow<Boolean> = repository.isAnnouncementBadgeEnabled

}