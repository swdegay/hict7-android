package dev.sethdegay.hict7.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import dev.sethdegay.hict7.core.database.model.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercises(exerciseEntities: List<ExerciseEntity>)

    @Update
    suspend fun updateExercises(exerciseEntities: List<ExerciseEntity>)

    @Delete
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity)
}
