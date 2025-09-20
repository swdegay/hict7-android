package dev.sethdegay.hict7.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import dev.sethdegay.hict7.core.model.Workout

data class WorkoutWithExercises(
    @Embedded
    val workoutEntity: WorkoutEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "workout_id"
    )
    val exercises: List<ExerciseEntity>
)

fun WorkoutWithExercises.asExternalModel(): Workout =
    workoutEntity.asExternalModel(exercises = exercises.map { it.asExternalModel() })
