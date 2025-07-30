package com.example.template.pagespeed.infrastructure.dataSources.remote

import com.example.template.app.utils.GOOGLE_PAGESPEED_KEY
import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.infrastructure.dataSources.PagespeedSource
import io.ktor.client.*
import io.ktor.client.plugins.timeout
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PagespeedDataSourceImpl(
    private val client: HttpClient,
    private val pageSpeedUrl: Url = Url(
        host = "www.googleapis.com",
        protocol = "https",
        path = "/pagespeedonline/v5/runPagespeed"
    ),
    private val apiKey: String = GOOGLE_PAGESPEED_KEY,
) : PagespeedSource {

    override suspend fun get(
        url: String,
        strategy: String,
    ): String {
        if (url.isEmpty()) {
            throw IllegalArgumentException(
                "URL cannot empty"
            )
        }
        val report = this.client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = this@PagespeedDataSourceImpl.pageSpeedUrl.host
                path(this@PagespeedDataSourceImpl.pageSpeedUrl.path!!)
                parameters.append(
                    name = "url",
                    value = url,
                )
                parameters.append(
                    name = "key",
                    value = this@PagespeedDataSourceImpl.apiKey
                )
                parameters.append(
                    name = "strategy",
                    value = strategy
                )
                parameters.append(
                    name = "category",
                    value = "performance"
                )
                parameters.append(
                    name = "category",
                    value = "best-practices"
                )
                parameters.append(
                    name = "category",
                    value = "accessibility"
                )
                parameters.append(
                    name = "category",
                    value = "seo"
                )
                parameters.append(
                    name = "category",
                    value = "pwa"
                )
            }
            contentType(type = ContentType.Application.Json)
            timeout {
               requestTimeoutMillis = 2.minutes.inWholeMilliseconds
            }

        }.bodyAsText()
        return report
    }

    override suspend fun pageExists(url: Url): Boolean {
        if (url.path.isNullOrEmpty()) throw IllegalArgumentException(
            "path não pode ser null"
        )

        val statusCode = this.client.post {
            url {
                protocol = URLProtocol.HTTPS
                host = url.host
                path(url.path!!)
            }
            contentType(type = ContentType.Application.Json)
        }.status

        println(statusCode)
        return statusCode == HttpStatusCode.OK
    }
}