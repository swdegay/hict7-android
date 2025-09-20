package dev.sethdegay.hict7.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.sethdegay.hict7.core.database.model.ExerciseEntity
import dev.sethdegay.hict7.core.database.model.WorkoutEntity
import dev.sethdegay.hict7.core.database.model.WorkoutWithExercises
import dev.sethdegay.hict7.core.model.IntervalType
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout WHERE bookmarked == TRUE")
    suspend fun bookmarkedWorkouts(): List<WorkoutEntity>

    @Query("SELECT * FROM exercise WHERE workout_id = :workoutId")
    suspend fun exercisesForWorkout(workoutId: Long): List<ExerciseEntity>

    @Query("SELECT * FROM exercise WHERE workout_id = :workoutId AND type IN (:allowedTypes)")
    suspend fun filteredExercisesForWorkout(
        workoutId: Long,
        allowedTypes: List<IntervalType>,
    ): List<ExerciseEntity>

    @Transaction
    suspend fun bookmarkedWorkouts(allowedTypes: List<IntervalType>?): List<WorkoutWithExercises> {
        val bookmarkedWorkouts = arrayListOf<WorkoutWithExercises>()
        val workouts = bookmarkedWorkouts()
        for (workout in workouts) {
            val id = workout.id ?: continue
            val exercises = if (allowedTypes == null) {
                exercisesForWorkout(id)
            } else {
                filteredExercisesForWorkout(id, allowedTypes)
            }
            bookmarkedWorkouts.add(WorkoutWithExercises(workout, exercises))
        }
        return bookmarkedWorkouts
    }

    @Transaction
    @Query("SELECT * FROM workout WHERE id == :id")
    fun workout(id: Long): Flow<WorkoutWithExercises>

    @Insert
    fun insertWorkout(workoutEntity: WorkoutEntity): Long

    @Update
    fun updateWorkout(workoutEntity: WorkoutEntity)

    @Delete
    fun deleteWorkoutAndExercises(
        workoutEntity: WorkoutEntity,
        exerciseEntities: List<ExerciseEntity>
    )
}