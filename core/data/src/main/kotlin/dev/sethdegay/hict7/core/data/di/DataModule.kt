package dev.sethdegay.hict7.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sethdegay.hict7.core.data.repository.CalendarEventRepository
import dev.sethdegay.hict7.core.data.repository.OfflineFirstCalendarEventRepository
import dev.sethdegay.hict7.core.data.repository.OfflineFirstUiStatePrefsRepository
import dev.sethdegay.hict7.core.data.repository.OfflineFirstUserPreferencesRepository
import dev.sethdegay.hict7.core.data.repository.OfflineFirstWorkoutRepository
import dev.sethdegay.hict7.core.data.repository.UiStatePrefsRepository
import dev.sethdegay.hict7.core.data.repository.UserPreferencesRepository
import dev.sethdegay.hict7.core.data.repository.WorkoutRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsCalendarEventRepository(
        calendarEventRepository: OfflineFirstCalendarEventRepository
    ): CalendarEventRepository

    @Binds
    internal abstract fun bindsWorkoutRepository(
        workoutRepository: OfflineFirstWorkoutRepository,
    ): WorkoutRepository

    @Binds
    internal abstract fun bindsUserPreferencesRepository(
        userPreferencesRepository: OfflineFirstUserPreferencesRepository,
    ): UserPreferencesRepository

    @Binds
    internal abstract fun bindsUiStatePrefsRepository(
        uiStatePrefsRepository: OfflineFirstUiStatePrefsRepository,
    ): UiStatePrefsRepository
}