package com.example.template.pagespeed.infrastructure.dataSources

import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.entities.ReportResult

interface ReportDataSource {
    suspend fun saveReport(
        dirId: String,
        reportResult: ReportResult,
    )

    suspend fun getDomains(): List<ReportPathsPageSpeed>
    suspend fun getSitemapReport(path: String): List<ReportPathsPageSpeed>
    suspend fun getReportPaths(path: String): List<ReportPathsPageSpeed>
    suspend fun openReport(path: String)
}