package dev.sethdegay.hict7.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sethdegay.hict7.core.datastore.ExerciseFilter
import dev.sethdegay.hict7.core.datastore.ExerciseFilterSerializer
import dev.sethdegay.hict7.core.datastore.UserPreferences
import dev.sethdegay.hict7.core.datastore.UserPreferencesSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(serializer = userPreferencesSerializer) {
            context.dataStoreFile("prefs.pb")
        }

    @Provides
    @Singleton
    internal fun providesExerciseFilterDataStore(
        @ApplicationContext context: Context,
        exerciseFilterSerializer: ExerciseFilterSerializer,
    ): DataStore<ExerciseFilter> =
        DataStoreFactory.create(serializer = exerciseFilterSerializer) {
            context.dataStoreFile("exercise_filter.pb")
        }
}