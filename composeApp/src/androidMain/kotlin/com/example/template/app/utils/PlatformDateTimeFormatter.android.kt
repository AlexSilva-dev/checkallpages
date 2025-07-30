package com.example.template.app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

internal actual object PlatformDateTimeFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    actual fun formatDate(timestampMillis: Long, style: DateStyle, localeId: String?, timeZone: TimeZone): String {
        val javaInstant = java.time.Instant.ofEpochMilli(timestampMillis)
        val locale = if (localeId != null) Locale.forLanguageTag(localeId) else Locale.getDefault()
        val formatter = when (style) {
            DateStyle.SHORT -> DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            DateStyle.MEDIUM -> DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            DateStyle.LONG -> DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            DateStyle.WEEKDAY_ONLY -> DateTimeFormatter.ofPattern("EEEE", locale)
        }
        return formatter.withLocale(locale).withZone(timeZone.toJavaZoneId()).format(javaInstant)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun formatTime(timestampMillis: Long, style: TimeStyle, localeId: String?, timeZone: TimeZone): String {
        val javaInstant = java.time.Instant.ofEpochMilli(timestampMillis)
        val locale = if (localeId != null) Locale.forLanguageTag(localeId) else Locale.getDefault()
        val formatter = when (style) {
            TimeStyle.SHORT -> DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            TimeStyle.MEDIUM -> DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        }
        return formatter.withLocale(locale).withZone(timeZone.toJavaZoneId()).format(javaInstant)
    }
}