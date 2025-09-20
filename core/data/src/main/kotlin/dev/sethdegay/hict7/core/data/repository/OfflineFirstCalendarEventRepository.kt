package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.common.toUnixTime
import dev.sethdegay.hict7.core.data.model.asEntity
import dev.sethdegay.hict7.core.database.dao.CalendarEventDao
import dev.sethdegay.hict7.core.database.model.CalendarEventEntity
import dev.sethdegay.hict7.core.database.model.asExternalModel
import dev.sethdegay.hict7.core.model.CalendarEvent
import dev.sethdegay.hict7.core.model.HeatMapLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class OfflineFirstCalendarEventRepository @Inject constructor(
    private val calendarEventDao: CalendarEventDao,
) : CalendarEventRepository {
    override suspend fun calendarEventsRange(
        start: Long,
        end: Long
    ): List<CalendarEvent> =
        calendarEventDao.calendarEventsRange(start, end).map { it.asExternalModel() }

    override fun eventCountsForRange(
        startDate: LocalDate,
        endDate: LocalDate,
        zoneId: ZoneId,
    ): Flow<Map<LocalDate, HeatMapLevel>> {
        val start = startDate.toUnixTime(zoneId)
        val end = endDate.plusDays(1).toUnixTime(zoneId)
        return calendarEventDao.eventCountsForRange(start = start, end = end).map { rawCounts ->
            val heatMap = mutableMapOf<LocalDate, HeatMapLevel>()
            val maxCount = rawCounts.maxOfOrNull { it.count } ?: 1
            rawCounts.forEach { dateEventCount ->
                val date = dateEventCount.date
                val heatMapLevel = mapCountToHeatMapLevel(dateEventCount.count, maxCount)
                heatMap[date] = heatMapLevel
            }
            heatMap
        }
    }

    override suspend fun insertCalendarEvent(calendarEvent: CalendarEvent) {
        calendarEvent.workout.id?.let { id ->
            calendarEventDao.insertCalendarEvent(
                calendarEventEntity = calendarEvent.asEntity(workoutId = id)
            )
        }
    }

    override suspend fun deleteCalendarEvents(calendarEvents: List<CalendarEvent>) {
        val eventEntities = arrayListOf<CalendarEventEntity>()
        calendarEvents.forEach { event ->
            val id = event.workout.id
            if (id != null) {
                eventEntities.add(event.asEntity(workoutId = id))
            }
        }
        calendarEventDao.deleteCalendarEvent(eventEntities)
    }
}

private fun mapCountToHeatMapLevel(count: Int, maxCount: Int): HeatMapLevel {
    val percentage = if (maxCount == 0) 0.0 else count.toDouble() / maxCount
    return when {
        percentage == 0.0 -> HeatMapLevel.Zero
        percentage <= 0.25 -> HeatMapLevel.One
        percentage <= 0.50 -> HeatMapLevel.Two
        percentage <= 0.75 -> HeatMapLevel.Three
        else -> HeatMapLevel.Four
    }
}
