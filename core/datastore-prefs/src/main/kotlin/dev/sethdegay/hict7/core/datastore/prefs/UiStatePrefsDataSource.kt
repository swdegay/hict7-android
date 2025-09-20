package dev.sethdegay.hict7.core.datastore.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UiStatePrefsDataSource(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val expandedIdKey = longPreferencesKey("expanded_key")
    }

    val expandedId: Flow<Long?> = dataStore.data.map { prefs -> prefs[expandedIdKey] }

    suspend fun setExpandedId(id: Long?) {
        dataStore.edit { prefs ->
            if (id == null) {
                prefs.remove(expandedIdKey)
            } else {
                prefs[expandedIdKey] = id
            }
        }
    }
}
