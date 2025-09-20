package dev.sethdegay.hict7.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sethdegay.hict7.core.database.Hict7Database
import dev.sethdegay.hict7.core.database.dao.CalendarEventDao
import dev.sethdegay.hict7.core.database.dao.ExerciseDao
import dev.sethdegay.hict7.core.database.dao.WorkoutDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesCalendarEventRepository(database: Hict7Database): CalendarEventDao =
        database.calendarEventDao()

    @Provides
    fun providesExerciseDao(database: Hict7Database): ExerciseDao = database.exerciseDao()

    @Provides
    fun providesWorkoutDao(database: Hict7Database): WorkoutDao = database.workoutDao()
}