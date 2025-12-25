package info.alihabibi.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import info.alihabibi.domain.local.keys.Keys
import info.alihabibi.domain.local.keys.Keys.APP_PREFERENCES
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.datastore by preferencesDataStore(name = APP_PREFERENCES)

class DatastoreManager(private val context: Context) {

    private val firstLaunch = booleanPreferencesKey(Keys.IS_FIRST_LAUNCH)

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

}