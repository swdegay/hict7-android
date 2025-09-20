package dev.sethdegay.hict7.core.data.model

import dev.sethdegay.hict7.core.database.model.WorkoutEntity
import dev.sethdegay.hict7.core.model.Workout

fun Workout.asEntity(): WorkoutEntity = WorkoutEntity(
    id = id,
    title = title,
    description = description,
    bookmarked = bookmarked,
    dateCreated = dateCreated,
    dateModified = dateModified,
)