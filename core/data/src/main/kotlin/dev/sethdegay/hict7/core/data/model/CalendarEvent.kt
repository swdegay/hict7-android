package dev.sethdegay.hict7.core.data.model

import dev.sethdegay.hict7.core.database.model.CalendarEventEntity
import dev.sethdegay.hict7.core.model.CalendarEvent

fun CalendarEvent.asEntity(workoutId: Long): CalendarEventEntity = CalendarEventEntity(
    id = id,
    workoutId = workoutId,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp,
    duration = duration,
)