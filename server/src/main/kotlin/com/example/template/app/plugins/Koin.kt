package com.example.template.app.plugins

import com.example.template.app.di.serverModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.koin() {
    install(Koin) {
        slf4jLogger()
        modules(modules = serverModule)
    }
}
