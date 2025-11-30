package info.alihabibi.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey

object Keys {
    const val APP_PREFERENCES = "APP_PREFERENCES"
    const val FIRST_LAUNCH = "APP_PREFERENCES"
    val IS_FIRST_LAUNCH = booleanPreferencesKey(FIRST_LAUNCH)
}