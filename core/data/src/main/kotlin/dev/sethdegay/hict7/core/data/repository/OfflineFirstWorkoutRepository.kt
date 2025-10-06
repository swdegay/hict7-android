package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.data.model.asEntity
import dev.sethdegay.hict7.core.database.dao.ExerciseDao
import dev.sethdegay.hict7.core.database.dao.WorkoutDao
import dev.sethdegay.hict7.core.database.model.asExternalModel
import dev.sethdegay.hict7.core.database.util.AppTransactionProvider
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.IntervalType
import dev.sethdegay.hict7.core.model.Workout
import javax.inject.Inject

class OfflineFirstWorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao,
    private val appTransactionProvider: AppTransactionProvider,
) : WorkoutRepository {

    override suspend fun bookmarkedWorkouts(allowedTypes: List<IntervalType>?): List<Workout> =
        workoutDao.bookmarkedWorkouts(allowedTypes).map { it.asExternalModel() }

    override suspend fun workout(id: Long): Workout =
        workoutDao.workout(id).asExternalModel()

    override suspend fun saveWorkout(workout: Workout): Long {
        val id = workout.id
        if (id == null) {
            return insertWorkoutAndExercises(workout)
        } else {
            updateWorkoutAndExercises(workout)
            return id
        }
    }

    override suspend fun deleteWorkout(id: Long) {
        val workout = workoutDao.workout(id).asExternalModel()
        workoutDao.deleteWorkoutAndExercises(
            workoutEntity = workout.asEntity(),
            exerciseEntities = workout.exercises.map { it.asEntity(id) }
        )
    }

    override suspend fun deleteExercise(workoutId: Long, exercise: Exercise) {
        exerciseDao.deleteExercise(exercise.asEntity(workoutId))
    }

    private suspend fun insertWorkoutAndExercises(workout: Workout): Long {
        if (workout.id != null) throw IllegalArgumentException() // TO DO report non null workout
        var workoutId: Long? = null
        appTransactionProvider.runInTransaction {
            workoutId = workoutDao.insertWorkout(workout.asEntity())
            exerciseDao.insertExercises(
                exerciseEntities = workout.exercises.map { it.asEntity(workoutId) },
            )
        }
        return workoutId ?: throw IllegalArgumentException()
    }

    private suspend fun updateWorkoutAndExercises(workout: Workout) {
        val id = workout.id
        if (id == null) return // TO DO report null workout
        appTransactionProvider.runInTransaction {
            workoutDao.updateWorkout(workout.asEntity())
            exerciseDao.updateExercises(
                exerciseEntities = workout.exercises.map { it.asEntity(id) }
            )
        }
    }
}
