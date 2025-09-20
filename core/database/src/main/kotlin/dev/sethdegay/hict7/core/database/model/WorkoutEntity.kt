package dev.sethdegay.hict7.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.Workout

@Entity(tableName = "workout")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "bookmarked")
    val bookmarked: Boolean,

    @ColumnInfo(name = "date_created")
    val dateCreated: Long,

    @ColumnInfo(name = "date_modified")
    val dateModified: Long,
)

fun WorkoutEntity.asExternalModel(exercises: List<Exercise> = emptyList()): Workout = Workout(
    id = id,
    title = title,
    description = description,
    bookmarked = bookmarked,
    dateCreated = dateCreated,
    dateModified = dateModified,
    exercises = exercises,
)
