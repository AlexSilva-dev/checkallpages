package com.example.template.app.infrastructure.dtos

import kotlinx.serialization.Serializable

@Serializable
enum class LocaleSettingsDto(
    val language: String,
    val country: String? = null
) {
    English(language = "en"),
    PortugueseBrazil(language = "pt", country = "BR")
}