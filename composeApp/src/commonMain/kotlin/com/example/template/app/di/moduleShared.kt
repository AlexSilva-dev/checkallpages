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
import com.example.template.pagespeed.domain.useCases.GetDomainPageSpeedReportUseCase
import com.example.template.pagespeed.domain.useCases.GetReportsPathsUseCase
import com.example.template.pagespeed.domain.useCases.GetSitemapPathsPageSpeedReportUseCase
import com.example.template.pagespeed.domain.useCases.OpenReportUseCase
import com.example.template.pagespeed.infrastructure.dataSources.PagespeedSource
import com.example.template.pagespeed.infrastructure.dataSources.ReportDataSource
import com.example.template.pagespeed.infrastructure.dataSources.local.ReportDataSourceLocal
import com.example.template.pagespeed.infrastructure.dataSources.remote.PagespeedDataSourceImpl
import com.example.template.pagespeed.infrastructure.repositories.PagespeedRepositoryImpl
import com.example.template.pagespeed.infrastructure.repositories.SitemapRepositoryImpl
import com.example.template.pagespeed.ui.viewModels.ReportsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
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

    single {
        OpenReportUseCase(
            pagespeedRepository = get()
        )
    }

    single {
        GetReportsPathsUseCase(
            pagespeedRepository = get()
        )
    }

    single {
        GetSitemapPathsPageSpeedReportUseCase(
            pagespeedRepository = get()
        )
    }

    single {
        GetDomainPageSpeedReportUseCase(
            pagespeedRepository = get()
        )
    }

    single {
        ReportsViewModel(
            getDomainPageSpeedReportUseCase = get(),
            getSitemapPathsPageSpeedReportUseCase = get(),
            getReportsPathsUseCase = get(),
            openReportUseCase = get()
        )
    }

    single<PagespeedSource> {
        PagespeedDataSourceImpl(
            client = get(),
            settingsDataStore = get(),
        )
    }

    single<PagespeedRepository> {
        PagespeedRepositoryImpl(
            pagespeedSource = get(),
            reportDataSource = get()
        )
    }

    single<ReportDataSource> {
        ReportDataSourceLocal()
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
