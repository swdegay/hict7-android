package dev.sethdegay.hict7.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import dev.sethdegay.hict7.core.model.CalendarEvent

data class CalendarEventWithWorkout(
    @Embedded
    val calendarEventEntity: CalendarEventEntity,

    @Relation(
        parentColumn = "workout_id",
        entityColumn = "id"
    )
    val workoutEntity: WorkoutEntity
)

fun CalendarEventWithWorkout.asExternalModel(): CalendarEvent = CalendarEvent(
    id = calendarEventEntity.id,
    workout = workoutEntity.asExternalModel(),
    startTimestamp = calendarEventEntity.startTimestamp,
    endTimestamp = calendarEventEntity.endTimestamp,
    duration = calendarEventEntity.duration,
)