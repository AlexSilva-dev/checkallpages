package com.example.template.app.domain.ports

import com.example.template.app.domain.entities.LocaleSettings

interface LocaleSettingsRepository {
    fun setLanguage(localeSettings: LocaleSettings)
    fun getLanguage(): LocaleSettings

}