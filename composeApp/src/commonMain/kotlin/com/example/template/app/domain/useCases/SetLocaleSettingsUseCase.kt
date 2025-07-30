package com.example.template.app.domain.useCases

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.domain.ports.LocaleSettingsRepository

class SetLocaleSettingsUseCase(
    private val repository: LocaleSettingsRepository
) {
    fun execute(localeSettings: LocaleSettings) {
        this.repository.setLanguage(localeSettings)
    }

    fun execute() {
        val localeSettings: LocaleSettings = this.repository.getLanguage()
        this.repository.setLanguage(
            localeSettings = localeSettings
        )
    }
}