package dev.sethdegay.hict7.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.sethdegay.hict7.core.database.model.CalendarEventEntity
import dev.sethdegay.hict7.core.database.model.CalendarEventWithWorkout
import dev.sethdegay.hict7.core.database.model.DateEventCount
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarEventDao {
    @Transaction
    @Query("SELECT * FROM calendar_event WHERE start_timestamp BETWEEN :start AND :end")
    suspend fun calendarEventsRange(start: Long, end: Long): List<CalendarEventWithWorkout>

    @Query(
        """
        SELECT
            strftime('%Y-%m-%d', start_timestamp, 'unixepoch', 'localtime') AS date,
            COUNT(id) AS count
        FROM calendar_event
        WHERE start_timestamp BETWEEN :start AND :end
        GROUP BY date
        ORDER BY date ASC
    """
    )
    fun eventCountsForRange(start: Long, end: Long): Flow<List<DateEventCount>>

    @Insert
    suspend fun insertCalendarEvent(calendarEventEntity: CalendarEventEntity)

    @Delete
    suspend fun deleteCalendarEvent(calendarEventEntities: List<CalendarEventEntity>)
}
