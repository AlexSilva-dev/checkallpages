package com.example.template.pagespeed.domain.entities

data class Urlset(
    val url: List<SitemapUrl>
)

data class SitemapUrl(
    val loc: String,
    val lastmod: String?
)