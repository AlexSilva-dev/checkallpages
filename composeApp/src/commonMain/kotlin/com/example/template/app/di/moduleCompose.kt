package com.example.template.app.di

import com.example.template.app.viewModels.AppViewModel
import com.example.template.pagespeed.ui.viewModels.GeneratePagespeedReportViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val moduleCompose: Module = module {
    viewModel {
        AppViewModel(
            setLocaleSettingsUseCase = get(),
            getLocaleSettingsUseCase = get()
        )
    }

    viewModel {
        GeneratePagespeedReportViewModel(
            generatePagespeedReportsUseCase = get(),
            settingsDataStore = get(),
        )
    }
}
