package dev.sethdegay.hict7.core.common

import java.text.DateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val unixTimeNow: Long
    get() = System.currentTimeMillis() / 1_000L

fun LocalDate.toUnixTime(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return this.atStartOfDay(zoneId)
        .toInstant()
        .toEpochMilli() / 1_000L
}

fun calculateDuration(start: Long, end: Long): Duration =
    (end - start).seconds

fun unixTimestampToShortTime(unixTime: Long, locale: Locale = Locale.getDefault()): String {
    val date = Date(unixTime * 1_000L)
    val formatter = DateFormat.getTimeInstance(DateFormat.SHORT, locale)
    return formatter.format(date)
}
