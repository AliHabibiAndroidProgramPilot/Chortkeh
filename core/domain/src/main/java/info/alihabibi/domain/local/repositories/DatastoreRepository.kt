package info.alihabibi.domain.local.repositories

import info.alihabibi.domain.models.Currencies
import info.alihabibi.domain.models.UserInfo
import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {

    suspend fun saveFirstLaunch(value: Boolean)
    val isFirstLaunch: Flow<Boolean>

    suspend fun saveSmsModalShownState(value: Boolean)
    val isSmsModalShown: Flow<Boolean>

    suspend fun savePreferredCurrency(value: String)
    val preferredCurrency: Flow<Currencies>

    suspend fun saveUserInfo(userInfo: UserInfo)
    val userAccountInfo: Flow<UserInfo>

}