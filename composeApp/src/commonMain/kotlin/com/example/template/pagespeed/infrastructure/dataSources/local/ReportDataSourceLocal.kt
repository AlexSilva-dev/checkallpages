package com.example.template.pagespeed.infrastructure.dataSources.local

import com.example.template.app.utils.DateStyle
import com.example.template.app.utils.PlatformFileManager
import com.example.template.app.utils.TimeStyle
import com.example.template.app.utils.UrlUtil
import com.example.template.app.utils.appendString
import com.example.template.app.utils.createDirectories
import com.example.template.app.utils.exists
import com.example.template.app.utils.formatDate
import com.example.template.app.utils.formatTime
import com.example.template.app.utils.openFileWithSystemDefault
import com.example.template.app.utils.resolve
import com.example.template.app.utils.writeString
import com.example.template.pagespeed.domain.entities.Category
import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.entities.ReportResult
import com.example.template.pagespeed.infrastructure.dataSources.ReportDataSource
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

const val PAGE = "page"
const val PERFORMANCE = "performance"
const val ACCESSIBILITY = "accessibility"
const val BEST_PRACTICES = "best-practices"
const val SEO = "seo"
const val AT_CREATE = "at-create"

const val REPORT_SUMMARY = "report.csv"

class ReportDataSourceLocal : ReportDataSource {

    private val csvHeader: String =
        "$PAGE,$PERFORMANCE,$ACCESSIBILITY,$BEST_PRACTICES,$SEO,$AT_CREATE"

    private val pageSpeedReportPath: String = "/pageSpeedReport"
    private val reportJsonInternalPath: String = "/report"

    override suspend fun saveReport(
        path: String,
        reportResult: ReportResult
    ) {
        val platform: PlatformFile = this.getPlatformFile(
            "${this.pageSpeedReportPath}/$path"
        )
        this.writeJson(platform, reportResult)
        this.writeCsv(reportResult, platform)
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun writeCsv(
        reportResult: ReportResult,
        platform: PlatformFile
    ) {
        val reportCsv: PlatformFile = platform.resolve(
            relative = REPORT_SUMMARY
        )

        if (!reportCsv.exists()) {
            reportCsv.writeString(
                string = this.csvHeader
            )
        }

        val category: Map<String, Category> =
            reportResult.lighthouseCategories
        val page: String = reportResult.url
        val performance: String =
            category.getValue(PERFORMANCE).score.toString()
        val accessibility: String =
            category.getValue(ACCESSIBILITY).score.toString()
        val bestPractices: String =
            category.getValue(BEST_PRACTICES).score.toString()
        val seo: String = category.getValue(SEO).score.toString()
        val now: Instant = Clock.System.now()
        val atCreate: String =
            "${now.formatTime(TimeStyle.MEDIUM)} ${
                now.formatDate(
                    DateStyle.MEDIUM
                )
            }"

        val text: String =
            "\n$page,$performance,$accessibility,$bestPractices,$seo,$atCreate"
        reportCsv.appendString(
            string = text
        )
    }

    private suspend fun writeJson(
        platform: PlatformFile,
        reportResult: ReportResult
    ) {
        var file = platform.resolve(
            relative = this.reportJsonInternalPath
        )
        if (!file.exists()) {
            file.createDirectories()
        }

        val reportName = this.createJsonFileName(reportResult)
        file = platform.resolve(
            relative = "${this.reportJsonInternalPath}/$reportName.json"
        )
        file.writeString(
            string = reportResult.jsonInText
        )
    }

    private suspend fun getPlatformFile(path: String): PlatformFile {

        var platform: PlatformFile? =
            PlatformFileManager.getPlatformFile()
        if (platform == null) throw Exception("Arquivo não foi criado")

        val pathNew = path.replace(platform.toString(), "")
        platform = platform.resolve(pathNew)
        PlatformFileManager.createDirectoryIfNotExist(
            platformFile = platform
        )
        return platform
    }

    private fun createJsonFileName(reportResult: ReportResult): String {
        var fileName = UrlUtil.getPath(reportResult.url)
        if (fileName.isEmpty()) fileName = "home"
        return fileName
    }

    override suspend fun getDomains(): List<ReportPathsPageSpeed> {
        val platform: PlatformFile = this.getPlatformFile(
            this.pageSpeedReportPath
        )
        val folders: List<PlatformFile> =
            PlatformFileManager.listFolders(platform)
        return folders.map {
            ReportPathsPageSpeed(
                name = it.name.replace("-", "."),
                path = it.toString(),
                atCreate = ""
            )
        }
    }

    override suspend fun getSitemapReport(path: String): List<ReportPathsPageSpeed> {
        val platform: PlatformFile = this.getPlatformFile(
            path
        )
        println(platform)
        val folders: List<PlatformFile> =
            PlatformFileManager.listFolders(platform)
        return folders.map {
            ReportPathsPageSpeed(
                name = it.name,
                path = it.toString(),
                atCreate = ""
            )
        }
    }


    override suspend fun getReportPaths(path: String): List<ReportPathsPageSpeed> {
        var platform: PlatformFile = this.getPlatformFile(
            this.pageSpeedReportPath
        )
        val pathNew = path.replace(platform.toString(), "")
        platform = platform.resolve(pathNew)
        val folders: List<PlatformFile> =
            PlatformFileManager.listFolders(platform)
        return folders.map {
            ReportPathsPageSpeed(
                name = it.name
                    .replace("-_", ":")
                    .replace("-", "."),
                path = it.toString(),
                atCreate = ""
            )
        }
    }

    override suspend fun openReport(path: String) {
        var platform: PlatformFile = this.getPlatformFile(
            this.pageSpeedReportPath
        )
        val pathNew = path
            .replace(platform.toString(), "")
            .replace(":", "-_")
            .replace(".", "-")

        platform = platform.resolve(pathNew)
        platform = platform.resolve(
            relative = REPORT_SUMMARY
        )

        openFileWithSystemDefault(
            filePath = platform.toString()
        )
    }
}
