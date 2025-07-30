package com.example.template.app.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

internal actual object PlatformDateTimeFormatter {
    actual fun formatDate(
        timestampMillis: Long,
        style: DateStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String {
        val javaInstant = java.time.Instant.ofEpochMilli(
            timestampMillis
        )

        val locale = if (localeId != null)
            Locale.forLanguageTag(localeId)
        else Locale.getDefault()

        val formatter = when (style) {
            DateStyle.SHORT -> DateTimeFormatter.ofLocalizedDate(
                java.time.format.FormatStyle.SHORT
            )
            DateStyle.MEDIUM -> DateTimeFormatter.ofLocalizedDate(
                java.time.format.FormatStyle.MEDIUM
            )
            DateStyle.LONG -> DateTimeFormatter.ofLocalizedDate(
                java.time.format.FormatStyle.LONG
            )
            DateStyle.WEEKDAY_ONLY -> DateTimeFormatter.ofPattern(
                "EEEE", locale
            )
        }
        return formatter
            .withLocale(locale)
            .withZone(timeZone.toJavaZoneId())
            .format(javaInstant)
    }

    actual fun formatTime(timestampMillis: Long, style: TimeStyle, localeId: String?, timeZone: TimeZone): String {
        val javaInstant = java.time.Instant.ofEpochMilli(timestampMillis)
        val locale = if (localeId != null) Locale.forLanguageTag(localeId) else Locale.getDefault()
        val formatter = when (style) {
            TimeStyle.SHORT -> DateTimeFormatter.ofLocalizedTime(java.time.format.FormatStyle.SHORT)
            TimeStyle.MEDIUM -> DateTimeFormatter.ofLocalizedTime(java.time.format.FormatStyle.MEDIUM)
        }
        return formatter.withLocale(locale).withZone(timeZone.toJavaZoneId()).format(javaInstant)
    }
}