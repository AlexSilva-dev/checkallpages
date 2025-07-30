package com.example.template.pagespeed.infrastructure.dtos.mappers

import com.example.template.pagespeed.domain.entities.SitemapUrl
import com.example.template.pagespeed.domain.entities.Urlset
import com.example.template.pagespeed.infrastructure.dtos.SitemapUrlDto
import com.example.template.pagespeed.infrastructure.dtos.UrlsetDto

fun UrlsetDto.toDomain(): Urlset {
    return Urlset(
        url = this.url.map { urlDto ->
            urlDto.toDomain()
        }
    )
}

fun SitemapUrlDto.toDomain(): SitemapUrl {
    return SitemapUrl(
        loc = this.loc,
        lastmod = this.lastmod
    )
}