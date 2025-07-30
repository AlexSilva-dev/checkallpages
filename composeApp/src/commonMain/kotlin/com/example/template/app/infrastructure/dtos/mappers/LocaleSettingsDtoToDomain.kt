package com.example.template.app.infrastructure.dtos.mappers

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.infrastructure.dtos.LocaleSettingsDto

fun LocaleSettingsDto.toDomain(): LocaleSettings {
    return when (this) {
        LocaleSettingsDto.English -> LocaleSettings.English
        LocaleSettingsDto.PortugueseBrazil -> LocaleSettings.PortugueseBrazil
    }
}