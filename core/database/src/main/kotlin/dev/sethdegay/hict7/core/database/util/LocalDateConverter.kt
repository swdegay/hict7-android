package dev.sethdegay.hict7.core.database.util

import androidx.room.TypeConverter
import java.time.LocalDate

internal class LocalDateConverter {

    @TypeConverter
    fun stringToLocalDate(string: String): LocalDate = LocalDate.parse(string)
}