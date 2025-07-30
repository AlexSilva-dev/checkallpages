package com.example.template

import com.example.template.app.module
import io.ktor.server.testing.testApplication
import kotlin.test.Test

class GeminiServiceTest {
    @Test
    fun requestGemini() = testApplication {
        application {
            module()
        }

    }
}