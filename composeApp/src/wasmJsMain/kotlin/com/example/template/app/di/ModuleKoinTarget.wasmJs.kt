package com.example.template.app.di

import com.example.template.app.infrastructure.dataSources.CreateSettingsLibrary
import org.koin.core.module.Module
import org.koin.dsl.module

actual val moduleTarget: Module = module {
    single {
        CreateSettingsLibrary()
    }
}