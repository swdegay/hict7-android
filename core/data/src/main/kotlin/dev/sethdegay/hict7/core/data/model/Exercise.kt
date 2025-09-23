package dev.sethdegay.hict7.core.data.model

import dev.sethdegay.hict7.core.database.model.ExerciseEntity
import dev.sethdegay.hict7.core.model.Exercise

fun Exercise.asEntity(workoutId: Long): ExerciseEntity = ExerciseEntity(
    id = id,
    workoutId = workoutId,
    title = title,
    duration = duration,
    type = type,
    order = order,
)