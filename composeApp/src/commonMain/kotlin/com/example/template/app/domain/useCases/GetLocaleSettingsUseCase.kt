package com.example.template.app.domain.useCases

import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.domain.ports.LocaleSettingsRepository

class GetLocaleSettingsUseCase(
    private val repository: LocaleSettingsRepository
) {
    fun execute(): LocaleSettings {
        return this.repository.getLanguage()
    }
}