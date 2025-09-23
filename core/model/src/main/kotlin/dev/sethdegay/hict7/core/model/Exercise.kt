package dev.sethdegay.hict7.core.model

import kotlin.time.Duration

data class Exercise(
    val id: Long? = null,
    val title: String,
    val duration: Duration,
    val type: IntervalType = IntervalType.NORMAL,
    val order: Int = -1,
)
