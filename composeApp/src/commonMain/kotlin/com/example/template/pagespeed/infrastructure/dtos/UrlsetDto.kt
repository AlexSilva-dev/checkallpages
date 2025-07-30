package com.example.template.pagespeed.infrastructure.dtos

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val SITEMAP_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9"

@Serializable
@XmlSerialName("urlset", SITEMAP_NAMESPACE, "") // Elemento raiz
data class UrlsetDto(
    @XmlElement(true) // Indica que é um elemento filho
    val url: List<SitemapUrlDto>
)

@Serializable
@XmlSerialName("url", SITEMAP_NAMESPACE, "") // Elemento sitemap
data class SitemapUrlDto(
    @XmlElement(true)
    val loc: String,
    @XmlElement(true)
    val lastmod: String? = null
)
