package com.example.template.app.utils

import kotlinx.datetime.TimeZone
import kotlin.time.ExperimentalTime
import kotlin.time.Instant



/**
 * Time format for user view
 */
@OptIn(ExperimentalTime::class)
fun Instant.formatTime(
    style: TimeStyle,
    localeId: String? = null,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    val millis = toEpochMilliseconds()
    return PlatformDateTimeFormatter.formatTime(
        timestampMillis = millis,
        style = style,
        localeId = localeId,
        timeZone = timeZone
    )
}




