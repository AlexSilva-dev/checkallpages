package com.example.template.pagespeed.infrastructure.repositories

import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.domain.entities.Urlset
import com.example.template.pagespeed.domain.ports.SitemapRepository
import com.example.template.pagespeed.infrastructure.dtos.UrlsetDto
import com.example.template.pagespeed.infrastructure.dtos.mappers.toDomain
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.DefaultXmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig.Companion.IGNORING_UNKNOWN_CHILD_HANDLER

class SitemapRepositoryImpl(
    private val client: HttpClient
): SitemapRepository {
    /*
         * Busca e analisa um sitemap XML, extraindo todas as URLs.
         * Esta função agora usa kotlinx.serialization para analisar o XML de forma
         * multiplataforma e lida com sitemaps de índice e de conteúdo.
         */
    @OptIn(ExperimentalXmlUtilApi::class)
    override suspend fun getUrlsFromSitemap(url: Url): Urlset? {
        println("Fetching sitemap from: ${url.fullUrl()}")
        val httpResponse: HttpResponse = try {
            client.get(url.fullUrl())
        } catch (e: Exception) {
            println("Error fetching sitemap content from ${url.fullUrl()}: ${e.message}")
            return null
        }
        val sitemapContent: String = httpResponse.bodyAsText()
        try {

            val urlSetDto: UrlsetDto = XML{
                policy = DefaultXmlSerializationPolicy(
                    unknownChildHandler = IGNORING_UNKNOWN_CHILD_HANDLER,
                    pedantic = true
                )
            }.decodeFromString(
                deserializer = UrlsetDto.serializer(),
                string = sitemapContent
            )

            return urlSetDto.toDomain()
        } catch (e: Exception) {
            println("deu ruim")
            println(e.message)
            println("/n/n/n")
        }
        return null

    }
}
