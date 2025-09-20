package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.model.CalendarEvent
import dev.sethdegay.hict7.core.model.HeatMapLevel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

interface CalendarEventRepository {
    suspend fun calendarEventsRange(start: Long, end: Long): List<CalendarEvent>
    fun eventCountsForRange(
        startDate: LocalDate,
        endDate: LocalDate,
        zoneId: ZoneId,
    ): Flow<Map<LocalDate, HeatMapLevel>>

    suspend fun insertCalendarEvent(calendarEvent: CalendarEvent)
    suspend fun deleteCalendarEvents(calendarEvents: List<CalendarEvent>)
}