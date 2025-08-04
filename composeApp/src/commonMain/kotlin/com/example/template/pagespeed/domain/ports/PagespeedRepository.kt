package com.example.template.pagespeed.domain.ports

import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.entities.ReportResult
import com.example.template.pagespeed.domain.entities.Url

interface PagespeedRepository {
    suspend fun generateReport(url: String): ReportResult
    suspend fun saveReport(
        dirId: String,
        reportResult: ReportResult
    ): Result<Unit>

    suspend fun getSitemapPath(url: Url): Url
    suspend fun getDomains(): List<ReportPathsPageSpeed>
    suspend fun getSitemapPaths(path: String): List<ReportPathsPageSpeed>
    suspend fun getReportPaths(path: String): List<ReportPathsPageSpeed>
    suspend fun openReport(path: String)
}