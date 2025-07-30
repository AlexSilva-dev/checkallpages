package com.example.template.pagespeed.domain.entities

data class Url(
    val host: String,
    val path: String? = null,
    val protocol: String
) {
    fun fullUrl(): String {
        return "${this.protocol}://${this.host}/${path}"
    }
}
