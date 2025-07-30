package com.example.template.app

import com.example.template.app.utils.SERVER_PORT
import com.example.template.app.plugins.configureDatabase
import com.example.template.app.plugins.configureRouting
import com.example.template.app.plugins.contentNegotiation
import com.example.template.app.plugins.cors
import com.example.template.app.plugins.koin
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

/**
 * A ordem da inicialização importa
 */
fun Application.module() {
    cors()
    koin()
    configureRouting()
    contentNegotiation()
    configureDatabase()
}