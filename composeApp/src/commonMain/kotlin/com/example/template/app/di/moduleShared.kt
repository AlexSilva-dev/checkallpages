package com.example.template.app.di

import com.example.template.app.domain.ports.LocaleSettingsRepository
import com.example.template.app.domain.useCases.GetLocaleSettingsUseCase
import com.example.template.app.domain.useCases.SetLocaleSettingsUseCase
import com.example.template.app.infrastructure.dataSources.SettingsDataStore
import com.example.template.app.infrastructure.dataSources.SettingsDataStoreImpl
import com.example.template.app.infrastructure.repository.LocaleSettingsRepositoryImpl
import com.example.template.app.infrastructure.services.LocaleSettingsService
import com.example.template.pagespeed.domain.ports.PagespeedRepository
import com.example.template.pagespeed.domain.ports.SitemapRepository
import com.example.template.pagespeed.domain.useCases.GeneratePagespeedReportsUseCase
import com.example.template.pagespeed.infrastructure.dataSources.PagespeedSource
import com.example.template.pagespeed.infrastructure.dataSources.remote.PagespeedDataSourceImpl
import com.example.template.pagespeed.infrastructure.repositories.PagespeedRepositoryImpl
import com.example.template.pagespeed.infrastructure.repositories.SitemapRepositoryImpl
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module


val moduleShared: Module = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint =
                            true // Formata o JSON para ser mais legível no log
                        ignoreUnknownKeys =
                            true // Ignora campos JSON que não estão nas suas data classes
                    }
                )
            }

        }
    }

    single<PagespeedSource> {
        PagespeedDataSourceImpl(
            client = get(),
        )
    }

    single<PagespeedRepository> {
        PagespeedRepositoryImpl(
            pagespeedSource = get()
        )
    }

    single<SitemapRepository> {
        SitemapRepositoryImpl(
            client = get()
        )
    }

    single {
        GeneratePagespeedReportsUseCase(
            pagespeedRepository = get(),
            sitemapRepository = get(),
        )
    }

    single {
        SetLocaleSettingsUseCase(
            repository = get()
        )
    }

    single {
        GetLocaleSettingsUseCase(
            repository = get()
        )
    }

    single<LocaleSettingsRepository> {
        LocaleSettingsRepositoryImpl(
            localeSettingsService = get()
        )
    }

    single {
        LocaleSettingsService(settingsDataStore = get())
    }

    single<SettingsDataStore> {
        SettingsDataStoreImpl(
            createSettingsLibrary = get()
        )
    }

}
