package com.example.template.app.infrastructure.services

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.infrastructure.dtos.LocaleSettingsDto
import com.example.template.app.infrastructure.dtos.mappers.toDto
import java.util.Locale

actual fun LocaleSettingsService.setLanguage(localeSettings: LocaleSettings) {
    // 1. Cria o objeto Locale do Java, assim como na versão Desktop.
    val localeNew = if (localeSettings.country.isNullOrEmpty()) {
        Locale(localeSettings.language)
    } else {
        Locale(localeSettings.language, localeSettings.country)
    }

    // 2. Cria uma lista de localidades compatível com o sistema.
    val appLocale = LocaleListCompat.create(localeNew)

    // 3. Define a localidade do aplicativo usando a API .
    // O sistema operacional irá persistir essa escolha.
    AppCompatDelegate.setApplicationLocales(appLocale)

    // todo remover essa implementaçao dentro dessa funçao, ela deve ser um serviço separado
    this.settingsDataStore.save<LocaleSettingsDto>(
        key = "language",
        serializer = LocaleSettingsDto.serializer(),
        value = localeSettings.toDto(),
    )

}