package dev.sethdegay.hict7.core.model

import kotlin.time.Duration

data class CalendarEvent(
    val id: Long? = null,
    val workout: Workout,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val duration: Duration,
)
