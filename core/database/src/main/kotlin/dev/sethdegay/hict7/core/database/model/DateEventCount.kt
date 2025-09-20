package dev.sethdegay.hict7.core.database.model

import java.time.LocalDate

data class DateEventCount(
    val date: LocalDate,
    val count: Int,
)
