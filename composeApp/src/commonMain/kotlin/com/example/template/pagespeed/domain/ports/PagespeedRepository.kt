package com.example.template.pagespeed.domain.ports

import com.example.template.pagespeed.domain.entities.Quiz
import com.example.template.pagespeed.domain.entities.SitemapUrl
import com.example.template.pagespeed.domain.entities.Topic
import com.example.template.pagespeed.domain.entities.Url

interface PagespeedRepository {
    suspend fun generateReport(url: String): String

    suspend fun getSitemapPath(url: Url): Url
}