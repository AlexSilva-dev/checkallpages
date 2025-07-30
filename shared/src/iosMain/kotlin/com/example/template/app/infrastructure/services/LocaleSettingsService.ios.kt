package com.example.template.app.infrastructure.services

import com.example.template.app.domains.entities.LocaleSettings
import platform.Foundation.NSUserDefaults

actual fun LocaleSettingsService.setLanguage(localeSettings: LocaleSettings) {
    val localeId: String = localeSettings.localeId
    val defaults = NSUserDefaults.standardUserDefaults
    defaults.setObject(listOf(localeId), "AppleLanguages")
}