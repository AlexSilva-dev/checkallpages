package com.example.template.pagespeed.infrastructure.dataSources

import com.example.template.pagespeed.domain.entities.Url

interface PagespeedSource {

    suspend fun get(url: String, strategy: String = "mobile"): String

    /**
     * Verifica se uma pagina existe
     */
    suspend fun pageExists(url: Url): Boolean
}