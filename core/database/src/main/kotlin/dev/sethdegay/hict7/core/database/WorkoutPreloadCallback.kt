package dev.sethdegay.hict7.core.database

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.sethdegay.hict7.core.common.unixTimeNow
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.IntervalType
import dev.sethdegay.hict7.core.model.Workout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

private const val DEBUG_TAG = "WORKOUT_PRELOAD"
private const val TIMEOUT_SECONDS = 10

class WorkoutPreloadCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
    /*
     * Example workout routine
     */
    private val homebodyRoutine = Workout(
        id = 1,
        title = "Homebody Routine",
        description = "Full-body workout, no equipment needed. Builds strength, improves heart health, and boosts energy.",
        bookmarked = true,
        dateCreated = -1,
        dateModified = -1,
        exercises = listOf(
            Exercise(title = "Warm-up", duration = 3.minutes, type = IntervalType.WARM_UP),
            Exercise(title = "Jumping Jacks", duration = 30.seconds),
            Exercise(title = "Wall Sit", duration = 30.seconds),
            Exercise(title = "Push Up", duration = 30.seconds),
            Exercise(title = "Abdominal Crunch", duration = 30.seconds),
            Exercise(title = "Step up onto Chair", duration = 30.seconds),
            Exercise(title = "Squat", duration = 30.seconds),
            Exercise(title = "Triceps dip on Chair", duration = 30.seconds),
            Exercise(title = "Plank", duration = 30.seconds),
            Exercise(title = "High Knees Running in Place", duration = 30.seconds),
            Exercise(title = "Lunge", duration = 30.seconds),
            Exercise(title = "Push Up and Rotation", duration = 30.seconds),
            Exercise(title = "Left Side Plank", duration = 30.seconds),
            Exercise(title = "Right Side Plank", duration = 30.seconds),
            Exercise(title = "Wall stand", duration = 3.minutes, type = IntervalType.COOL_DOWN),
        ).let {
            arrayListOf<Exercise>().apply {
                it.forEachIndexed { index, exercise ->
                    add(exercise)
                    if (index != it.lastIndex
                        && it[index].type == IntervalType.NORMAL
                        && it[index + 1].type != IntervalType.COOL_DOWN
                    ) {
                        add(
                            Exercise(
                                title = "Rest",
                                duration = 10.seconds,
                                type = IntervalType.REST,
                            )
                        )
                    }
                }
            }.mapIndexed { i, exercise -> exercise.copy(order = i + 1) }
        },
    )

    override fun onCreate(db: SupportSQLiteDatabase) {
        scope.launch {
            try {
                withTimeout(TIMEOUT_SECONDS.seconds) {
                    if (isDbEmpty(db)) {
                        val now = unixTimeNow
                        homebodyRoutine.copy(
                            dateCreated = now,
                            dateModified = now,
                        ).addToDb(db)
                    } else {
                        Log.d(DEBUG_TAG, "DB is not empty")
                    }
                }
            } catch (e: TimeoutCancellationException) {
                Log.e(DEBUG_TAG, "Reached timeout ${e.message}")
            } catch (e: SQLiteException) {
                Log.e(DEBUG_TAG, "SQL exception ${e.message}")
            }
        }
    }

    private fun isDbEmpty(db: SupportSQLiteDatabase): Boolean {
        val cursor = db.query("SELECT COUNT(*) FROM workout")
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count == 0
    }

    private fun Workout.addToDb(db: SupportSQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL(
                "INSERT INTO workout (id, title, description, bookmarked, date_created, date_modified) " +
                        "VALUES ($id, '$title', '$description', $bookmarked, $dateCreated, $dateModified)"
            )
            exercises.forEach {
                db.execSQL(
                    "INSERT INTO exercise (workout_id, title, duration, type, list_order) " +
                            "VALUES ($id, '${it.title}', ${it.duration.toLong(DurationUnit.SECONDS)}, '${it.type.name}', ${it.order})"
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}