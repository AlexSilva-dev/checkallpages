package com.example.template.app.infrastructure.dtos.mappers

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.infrastructure.dtos.LocaleSettingsDto

fun LocaleSettings.toDto(): LocaleSettingsDto {
    return when (this) {
        LocaleSettings.English -> LocaleSettingsDto.English
        LocaleSettings.PortugueseBrazil -> LocaleSettingsDto.PortugueseBrazil

        else -> {
            LocaleSettingsDto.English
        }
    }
}