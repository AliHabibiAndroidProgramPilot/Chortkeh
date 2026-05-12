package info.alihabibi.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DatastoreRepositoryImpl(
    private val datastore: DatastoreManager
) : DatastoreRepository {

    override suspend fun saveFirstLaunch(value: Boolean) {
        datastore.saveFirstLaunch(value = value)
    }

    override val isFirstLaunch: Flow<Boolean>
        get() = datastore.isFirstLaunch
            .flowOn(Dispatchers.IO)

    override suspend fun saveSmsModalShownState(value: Boolean) {
        datastore.saveSmsModalState(value = value)
    }

    override val isSmsModalShown: Flow<Boolean>
        get() = datastore.isSmsModalShown
            .flowOn(Dispatchers.IO)

}