package com.example.template.pagespeed.infrastructure.repositories

import com.example.template.pagespeed.domain.entities.SitemapUrl
import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.domain.ports.PagespeedRepository
import com.example.template.pagespeed.infrastructure.dataSources.PagespeedSource
import kotlin.uuid.ExperimentalUuidApi

class PagespeedRepositoryImpl(
    val pagespeedSource: PagespeedSource
): PagespeedRepository {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun generateReport(url: String): String {
        val report: String = this.pagespeedSource.get(
            url = url
        )

        return ""
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
}