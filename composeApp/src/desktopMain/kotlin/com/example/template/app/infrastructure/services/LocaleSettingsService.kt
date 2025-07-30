package com.example.template.app.infrastructure.services

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.infrastructure.dtos.LocaleSettingsDto
import com.example.template.app.infrastructure.dtos.mappers.toDto
import java.util.Locale

actual fun LocaleSettingsService.setLanguage(localeSettings: LocaleSettings) {
    val localeNew = if (localeSettings.country.isNullOrEmpty())
        Locale.of(localeSettings.language)
    else Locale.of(localeSettings.language, localeSettings.country)

    // Setting the default locale
    Locale.setDefault(localeNew)
    this.settingsDataStore.save<LocaleSettingsDto>(
        key = "language",
        serializer = LocaleSettingsDto.serializer(),
        value = localeSettings.toDto(),
    )

}