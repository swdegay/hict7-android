package dev.sethdegay.hict7.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sethdegay.hict7.core.database.dao.CalendarEventDao
import dev.sethdegay.hict7.core.database.dao.ExerciseDao
import dev.sethdegay.hict7.core.database.dao.WorkoutDao
import dev.sethdegay.hict7.core.database.model.CalendarEventEntity
import dev.sethdegay.hict7.core.database.model.ExerciseEntity
import dev.sethdegay.hict7.core.database.model.WorkoutEntity
import dev.sethdegay.hict7.core.database.util.DurationConverter
import dev.sethdegay.hict7.core.database.util.LocalDateConverter

@Database(
    entities = [
        CalendarEventEntity::class,
        ExerciseEntity::class,
        WorkoutEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    value = [
        DurationConverter::class,
        LocalDateConverter::class,
    ],
)
internal abstract class Hict7Database : RoomDatabase() {
    abstract fun calendarEventDao(): CalendarEventDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
}