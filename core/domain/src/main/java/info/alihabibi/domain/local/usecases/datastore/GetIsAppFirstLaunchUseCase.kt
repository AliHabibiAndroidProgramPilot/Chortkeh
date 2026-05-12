package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import kotlinx.coroutines.flow.Flow

class GetIsAppFirstLaunchUseCase(
    private val repository: DatastoreRepository
) {

    operator fun invoke(): Flow<Boolean> = repository.isFirstLaunch

}