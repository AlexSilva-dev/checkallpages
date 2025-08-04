package com.example.template.pagespeed.infrastructure.dataSources

import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.domain.entities.ReportResult

interface PagespeedSource {

    suspend fun get(url: String, strategy: String = "mobile"): ReportResult

    /**
     * Verifica se uma pagina existe
     */
    suspend fun pageExists(url: Url): Boolean
}