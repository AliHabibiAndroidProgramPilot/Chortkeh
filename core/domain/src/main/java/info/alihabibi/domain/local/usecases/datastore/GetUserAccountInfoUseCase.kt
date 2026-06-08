package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import info.alihabibi.domain.models.UserAccountInfo
import kotlinx.coroutines.flow.Flow

class GetUserAccountInfoUseCase(
    private val repository: DatastoreRepository
) {

    operator fun invoke(): Flow<UserAccountInfo> = repository.userAccountInfo

}