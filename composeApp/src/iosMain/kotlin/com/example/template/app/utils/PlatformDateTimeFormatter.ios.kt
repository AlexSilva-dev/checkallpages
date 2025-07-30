package com.example.template.app.utils

import kotlinx.datetime.TimeZone
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterLongStyle
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterNoStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localTimeZone
import platform.SensorKit.dateWithSRAbsoluteTime

internal actual object PlatformDateTimeFormatter {
    actual fun formatDate(
        timestampMillis: Long,
        style: DateStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String {
        val nsDate: NSDate = NSDate.dateWithSRAbsoluteTime(timestampMillis/ 1000.0)
        val nsDateFormatter = NSDateFormatter()
        nsDateFormatter.timeStyle = NSDateFormatterNoStyle
        nsDateFormatter.locale = if (localeId != null)
            NSLocale.currentLocale
        else NSLocale()
        nsDateFormatter.timeZone = NSTimeZone.localTimeZone

        when (style) {
            DateStyle.SHORT -> nsDateFormatter.dateStyle = NSDateFormatterShortStyle
            DateStyle.MEDIUM -> nsDateFormatter.dateStyle = NSDateFormatterMediumStyle
            DateStyle.LONG -> nsDateFormatter.dateStyle = NSDateFormatterLongStyle
            DateStyle.WEEKDAY_ONLY -> nsDateFormatter.dateFormat = "EEEE"
        }

        return nsDateFormatter.stringFromDate(nsDate)}

    actual fun formatTime(
        timestampMillis: Long,
        style: TimeStyle,
        localeId: String?,
        timeZone: TimeZone
    ): String {
        val nsDate = NSDate.dateWithTimeIntervalSince1970(timestampMillis / 1000.0)
        val nsFormatter = NSDateFormatter()
        nsFormatter.dateStyle = NSDateFormatterNoStyle
        nsFormatter.locale = if (localeId != null) NSLocale(localeId) else NSLocale.currentLocale
        nsFormatter.timeZone = NSTimeZone.localTimeZone

        when (style) {
            TimeStyle.SHORT -> nsFormatter.timeStyle = NSDateFormatterShortStyle
            TimeStyle.MEDIUM -> nsFormatter.timeStyle = NSDateFormatterMediumStyle
        }

        return nsFormatter.stringFromDate(nsDate)
    }
}