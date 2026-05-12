package info.alihabibi.domain.local.repositories

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {

    suspend fun saveFirstLaunch(value: Boolean)
    val isFirstLaunch: Flow<Boolean>

    suspend fun saveSmsModalShownState(value: Boolean)
    val isSmsModalShown: Flow<Boolean>

}