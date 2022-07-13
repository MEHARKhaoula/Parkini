package com.example.ProjetParking

import androidx.room.TypeConverter
import java.sql.Time
import java.time.LocalTime
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.let { it.time.toLong()}
    }




}