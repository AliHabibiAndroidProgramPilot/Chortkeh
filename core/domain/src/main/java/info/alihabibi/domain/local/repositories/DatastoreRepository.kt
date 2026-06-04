package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.Currencies
import info.alihabibi.domain.models.UserAccountInfo
import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {

    suspend fun saveFirstLaunch(value: Boolean)
    val isFirstLaunch: Flow<Boolean>

    suspend fun saveSmsModalShownState(value: Boolean)
    val isSmsModalShown: Flow<Boolean>

    suspend fun savePreferredCurrency(value: String)
    val preferredCurrency: Flow<Currencies>

    suspend fun saveUserInfo(userAccountInfo: UserAccountInfo)
    val userAccountInfo: Flow<UserAccountInfo>

}