package info.alihabibi.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import info.alihabibi.domain.local.keys.Keys
import info.alihabibi.domain.local.keys.Keys.APP_PREFERENCES
import info.alihabibi.domain.models.Currencies
import info.alihabibi.domain.models.Genders
import info.alihabibi.domain.models.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.datastore by preferencesDataStore(name = APP_PREFERENCES)

class DatastoreManager(private val context: Context) {

    private val firstLaunch = booleanPreferencesKey(Keys.IS_FIRST_LAUNCH)
    private val smsModalShown = booleanPreferencesKey(Keys.SMS_MODAL_SHOWN)
    private val currency = stringPreferencesKey(Keys.PREFERRED_CURRENCY)
    private val userFullName = stringPreferencesKey(Keys.USER_FULL_NAME)
    private val userPhone = stringPreferencesKey(Keys.USER_PHONE)
    private val userGender = stringPreferencesKey(Keys.USER_GENDER)

    suspend fun saveFirstLaunch(value: Boolean) = withContext(Dispatchers.IO) {
        context.datastore.edit { prefs ->
            prefs[firstLaunch] = value
        }
    }

    val isFirstLaunch: Flow<Boolean>
        get() =
            context.datastore.data
                .catch { emit(emptyPreferences()) }
                .map { prefs ->
                    prefs[firstLaunch] ?: true
                }
                .flowOn(Dispatchers.IO)

    suspend fun saveSmsModalState(value: Boolean) = withContext(Dispatchers.IO) {
        context.datastore.edit { prefs ->
            prefs[smsModalShown] = value
        }
    }

    val isSmsModalShown: Flow<Boolean>
        get() =
            context.datastore.data
                .catch { emptyPreferences() }
                .map { prefs ->
                    prefs[smsModalShown] ?: false
                }
                .flowOn(Dispatchers.IO)

    suspend fun savePreferredCurrency(value: String) = withContext(Dispatchers.IO) {
        context.datastore.edit { pref ->
            pref[currency] = value
        }
    }

    val preferredCurrency: Flow<Currencies>
        get() =
            context.datastore.data
                .catch { emptyPreferences() }
                .map { pref ->
                    Currencies.entries.firstOrNull {
                        it.name == pref[currency].orEmpty()
                    } ?: Currencies.TOMAN
                }
                .flowOn(Dispatchers.IO)

    suspend fun saveUserAccountInfo(userInfo: UserInfo) {
        context.datastore.edit { pref ->
            pref[userFullName] = userInfo.fullName
            pref[userPhone] = userInfo.phone
            pref[userGender] = userInfo.gender.name
        }
    }

    val userAccountInfo: Flow<UserInfo>
        get() =
            context.datastore.data
                .catch { UserInfo(fullName = "", phone = "", gender = Genders.UNKNOW) }
                .map { pref ->
                    //TODO fix data store formatting decision for ui
                    UserInfo(
                        fullName = pref[userFullName].orEmpty(),
                        phone = pref[userPhone].orEmpty().chunked(4).joinToString(" "),
                        gender = pref[userGender]?.let { enumValueOf<Genders>(it) } ?: Genders.UNKNOW
                    )
                }
                .flowOn(Dispatchers.IO)

}