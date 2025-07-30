package com.example.template.app.plugins

import com.example.template.app.utils.IS_DEVELOPMENT
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.application.install

fun Application.cors() {
    install(CORS) {
        if (IS_DEVELOPMENT) {
            anyHost()
        } else {
            anyHost()
        }
        // Regras que se aplicam a ambos os ambientes
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)

        // Se você precisar expor algum header na resposta para o cliente
        // exposeHeader("X-My-Custom-Header")

        // Habilita o envio de cookies e outros tipos de credenciais
        allowCredentials = true
    }
}