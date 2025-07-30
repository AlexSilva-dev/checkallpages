package com.example.template.app.utils

import kotlinx.datetime.TimeZone
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


/**
 * Date format for user view
 */
@OptIn(ExperimentalTime::class)
fun Instant.formatDate(
    style: DateStyle,
    localeId: String? = null,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    val millis = this.toEpochMilliseconds()
    return PlatformDateTimeFormatter.formatDate(
        millis, style, localeId, timeZone)
}


