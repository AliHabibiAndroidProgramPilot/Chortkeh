package info.alihabibi.domain.local.usecases.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import info.alihabibi.domain.models.UserAccountInfo

class SaveUserAccountInfoUseCase(
    private val repository: DatastoreRepository
) {

    suspend operator fun invoke(userInfo: UserAccountInfo) = repository.saveUserInfo(userInfo)

}