package com.example.template.app.infrastructure.repository

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.domain.ports.LocaleSettingsRepository
import com.example.template.app.infrastructure.services.LocaleSettingsService
import com.example.template.app.infrastructure.services.setLanguage

class LocaleSettingsRepositoryImpl(
    private val localeSettingsService: LocaleSettingsService
): LocaleSettingsRepository {
    override fun setLanguage(localeSettings: LocaleSettings) {
        this.localeSettingsService.setLanguage(localeSettings)
    }

    override fun getLanguage(): LocaleSettings {
        return this.localeSettingsService.getLanguage()
    }
}