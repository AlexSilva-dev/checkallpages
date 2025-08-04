package com.example.template.pagespeed.infrastructure.repositories

import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.entities.ReportResult
import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.domain.ports.PagespeedRepository
import com.example.template.pagespeed.infrastructure.dataSources.PagespeedSource
import com.example.template.pagespeed.infrastructure.dataSources.ReportDataSource
import kotlin.uuid.ExperimentalUuidApi

class PagespeedRepositoryImpl(
    val pagespeedSource: PagespeedSource,
    val reportDataSource: ReportDataSource,
) : PagespeedRepository {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun generateReport(url: String): ReportResult {
        val report: ReportResult = this.pagespeedSource.get(
            url = url
        )

        return report
    }

    override suspend fun saveReport(
        dirId: String,
        reportResult: ReportResult,
    ): Result<Unit> {
        try {
            this.reportDataSource.saveReport(
                dirId = dirId,
                reportResult = reportResult,
            )
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getSitemapPath(url: Url): Url {
        val possiblePaths: List<String> = listOf(
            "/sitemap.xml",
            "/sitemap1.xml",
            "/sitemap_index.xml",
            "/sitemap/sitemap.xml"
        )

        val sitemap: String? = possiblePaths.find { possiblePath ->
            val possibleUrl: Url = url.copy(path = possiblePath)
            this.pagespeedSource.pageExists(possibleUrl)
        }
        if (sitemap.isNullOrEmpty()) {
            throw IllegalStateException(
                "Sitemap não foi encontrado"
            )
        }

        val sitemapUrl: Url = url.copy(
            path = sitemap
        )
        return sitemapUrl
    }

    override suspend fun getDomains(): List<ReportPathsPageSpeed> {
        return this.reportDataSource.getDomains()
    }

    override suspend fun getSitemapPaths(path: String): List<ReportPathsPageSpeed> {
        return this.reportDataSource.getSitemapReport(
            path = path
        )
    }

    override suspend fun getReportPaths(path: String): List<ReportPathsPageSpeed> {
        return this.reportDataSource.getReportPaths(
            path = path
        )
    }

    override suspend fun openReport(path: String) {
        this.reportDataSource.openReport(
            path = path
        )

    }
}