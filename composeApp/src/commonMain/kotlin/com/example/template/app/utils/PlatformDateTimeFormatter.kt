package com.example.template.app.utils

import kotlinx.datetime.TimeZone


enum class TimeStyle {
    SHORT,  // Ex: 18:31 (BR), 6:31 PM (US)
    MEDIUM  // Ex: 18:31:44 (BR), 6:31:44 PM (US)
}

enum class DateStyle {
    SHORT,
    MEDIUM,
    LONG,
    WEEKDAY_ONLY
}

/**
 * SERVIÇO DE FORMATAÇÃO (O CONTRATO)
 * Este é o nosso objeto de serviço desacoplado. Ele só sabe lidar com Longs.
 * Esta é a única parte que usa 'expect'.
 */
internal expect object PlatformDateTimeFormatter {
    fun formatDate(
        timestampMillis: Long,
        style: DateStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String

    fun formatTime(
        timestampMillis: Long,
        style: TimeStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String
}