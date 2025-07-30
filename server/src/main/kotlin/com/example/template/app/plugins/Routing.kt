package com.example.template.app.plugins

import com.example.template.ai.routes.quiz
import io.ktor.server.application.Application


fun Application.configureRouting() {
    quiz()
}