package info.alihabibi.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import info.alihabibi.domain.models.Currencies
import info.alihabibi.domain.models.UserAccountInfo
import kotlinx.coroutines.flow.Flow

class DatastoreRepositoryImpl(
    private val datastore: DatastoreManager
) : DatastoreRepository {

    override suspend fun saveFirstLaunch(value: Boolean) {
        datastore.saveFirstLaunch(value = value)
    }

    override val isFirstLaunch: Flow<Boolean>
        get() = datastore.isFirstLaunch

    override suspend fun saveSmsModalShownState(value: Boolean) {
        datastore.saveSmsModalState(value = value)
    }

    override val isSmsModalShown: Flow<Boolean>
        get() = datastore.isSmsModalShown

    override suspend fun savePreferredCurrency(value: String) {
        datastore.savePreferredCurrency(value = value)
    }

    override val preferredCurrency: Flow<Currencies>
        get() = datastore.preferredCurrency

    override suspend fun saveUserInfo(userAccountInfo: UserAccountInfo) {
        datastore.saveUserAccountInfo(userAccountInfo)
    }

    override val userAccountInfo: Flow<UserAccountInfo>
        get() = datastore.userAccountInfo

}