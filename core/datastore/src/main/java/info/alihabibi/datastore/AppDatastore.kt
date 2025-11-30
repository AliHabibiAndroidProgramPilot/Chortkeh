package info.alihabibi.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import info.alihabibi.datastore.Keys.APP_PREFERENCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore by preferencesDataStore(name = APP_PREFERENCES)

class DatastoreManager(private val context: Context) {

    suspend fun saveFirstLaunch(value: Boolean) {
        context.datastore.edit { prefs ->
            prefs[Keys.IS_FIRST_LAUNCH] = value
        }
    }

    val isFirstLaunch: Flow<Boolean> get() =
        context.datastore.data.map { prefs ->
            prefs[Keys.IS_FIRST_LAUNCH] ?: false
        }

}