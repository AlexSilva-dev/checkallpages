package com.example.template.app.domain.entities

enum class LocaleSettings(
    val language: String,
    val country: String? = null
) {
    English(language = "en"),
    PortugueseBrazil(language = "pt", country = "BR"),
    French(language = "fr", country = "FR"),
    German(language = "de", country = "DE"),
    Japanese(language = "ja", country = "JP");

    /**
     * Propriedade computada que converte os campos 'language' e 'country'
     * para o formato de ID de localidade padrão (ex: "pt-BR").
     */
    val localeId: String
        get() = if (country.isNullOrEmpty()) {
            language
        } else {
            "$language-$country"
        }
}