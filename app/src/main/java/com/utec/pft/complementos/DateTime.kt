package com.utec.pft.complementos

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class DateTime(private val context: Context) {

    fun getCurrentDateTimeFormatted(format: String): String {
        val currentDateTime = Date()
        val deviceTimeZone = TimeZone.getDefault()
        val dateTimeFormat = SimpleDateFormat(format)
        dateTimeFormat.timeZone = deviceTimeZone
        return dateTimeFormat.format(currentDateTime)
    }
}