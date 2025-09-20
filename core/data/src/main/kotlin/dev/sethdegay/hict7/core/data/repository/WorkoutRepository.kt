package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.IntervalType
import dev.sethdegay.hict7.core.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun bookmarkedWorkouts(allowedTypes: List<IntervalType>?): List<Workout>

    fun workoutFlow(id: Long): Flow<Workout>

    suspend fun workout(id: Long): Workout

    suspend fun saveWorkout(workout: Workout): Long

    suspend fun deleteWorkout(id: Long)

    suspend fun deleteExercise(workoutId: Long, exercise: Exercise)
}