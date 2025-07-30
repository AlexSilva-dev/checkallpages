package com.example.template.app.viewModels

import androidx.lifecycle.ViewModel
import com.example.template.app.domain.entities.LocaleSettings
import com.example.template.app.domain.useCases.GetLocaleSettingsUseCase
import com.example.template.app.domain.useCases.SetLocaleSettingsUseCase
import org.koin.core.component.KoinComponent

class AppViewModel(
    private val setLocaleSettingsUseCase: SetLocaleSettingsUseCase,
    private val getLocaleSettingsUseCase: GetLocaleSettingsUseCase,
): ViewModel(), KoinComponent {

    init {
        this.setLocaleSettingsUseCase.execute(
            localeSettings = LocaleSettings.PortugueseBrazil
        )
    }

    fun getLocaleSettings(): LocaleSettings {
        return this.getLocaleSettingsUseCase.execute()
    }
}