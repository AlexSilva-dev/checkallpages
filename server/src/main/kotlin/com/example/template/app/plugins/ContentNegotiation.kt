package com.example.template.app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun Application.contentNegotiation() {
    install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
}