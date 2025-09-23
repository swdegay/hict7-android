package dev.sethdegay.hict7.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.IntervalType
import kotlin.time.Duration

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "workout_id")
    val workoutId: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "duration")
    val duration: Duration,

    @ColumnInfo(name = "type")
    val type: IntervalType,

    @ColumnInfo(name = "list_order")
    val order: Int,
)

fun ExerciseEntity.asExternalModel(): Exercise = Exercise(
    id = id,
    title = title,
    duration = duration,
    type = type,
    order = order,
)