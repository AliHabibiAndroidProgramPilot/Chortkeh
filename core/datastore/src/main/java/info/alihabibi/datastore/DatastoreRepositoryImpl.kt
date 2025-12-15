package info.alihabibi.datastore

import info.alihabibi.domain.local.repositories.DatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class DatastoreRepositoryImpl(
    private val datastore: DatastoreManager
) : DatastoreRepository {

    override suspend fun saveFirstLaunch(value: Boolean) {
        datastore.saveFirstLaunch(value = value)
    }

    override val isFirstLaunch: Flow<Boolean>
        get() = datastore.isFirstLaunch
            .catch { emit(false) }
            .flowOn(Dispatchers.IO)

}