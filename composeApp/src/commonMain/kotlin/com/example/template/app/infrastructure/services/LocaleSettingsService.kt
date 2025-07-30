package com.example.template.app.infrastructure.services

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.infrastructure.dataSources.SettingsDataStore
import com.example.template.app.infrastructure.dtos.LocaleSettingsDto
import com.example.template.app.infrastructure.dtos.mappers.toDomain

class LocaleSettingsService(
    val settingsDataStore: SettingsDataStore
) {
    fun getLanguage(): LocaleSettings {
        val localeSettingsDto: LocaleSettingsDto = this.settingsDataStore
            .getObject<LocaleSettingsDto>(
                key = "language",
                default = LocaleSettingsDto.PortugueseBrazil,
                serializer = LocaleSettingsDto.serializer()
            )
        return localeSettingsDto.toDomain()
    }
}

expect fun LocaleSettingsService.setLanguage(localeSettings: LocaleSettings)