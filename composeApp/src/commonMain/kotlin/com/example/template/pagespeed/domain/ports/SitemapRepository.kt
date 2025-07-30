package com.example.template.pagespeed.domain.ports

import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.domain.entities.Urlset

interface SitemapRepository {
    suspend fun getUrlsFromSitemap(url: Url): Urlset?
}