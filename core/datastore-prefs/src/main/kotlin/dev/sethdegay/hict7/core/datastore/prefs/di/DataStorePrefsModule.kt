package dev.sethdegay.hict7.core.datastore.prefs.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sethdegay.hict7.core.datastore.prefs.UiStatePrefsDataSource
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs_ui")

@Module
@InstallIn(SingletonComponent::class)
object DataStorePrefsModule {
    @Provides
    @Singleton
    internal fun providesUiStatePrefsDataSource(@ApplicationContext context: Context): UiStatePrefsDataSource =
        UiStatePrefsDataSource(context.dataStore)
}
