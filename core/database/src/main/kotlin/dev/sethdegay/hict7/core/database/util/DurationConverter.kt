package dev.sethdegay.hict7.core.database.util

import androidx.room.TypeConverter
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class DurationConverter {

    companion object {
        private val DURATION_UNIT = DurationUnit.SECONDS
    }

    @TypeConverter
    fun longToDuration(value: Long): Duration = value.toDuration(DURATION_UNIT)

    @TypeConverter
    fun durationToLong(duration: Duration): Long = duration.toLong(DURATION_UNIT)
}