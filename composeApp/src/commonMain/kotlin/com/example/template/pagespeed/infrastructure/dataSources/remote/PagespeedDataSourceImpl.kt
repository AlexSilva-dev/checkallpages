package com.example.template.pagespeed.infrastructure.dataSources.remote

import com.example.template.app.infrastructure.dataSources.SettingsDataStore
import com.example.template.app.utils.API_KEY_SETTING_KEY
import com.example.template.pagespeed.Exceptions.ApiKeyNotFoundException
import com.example.template.pagespeed.domain.entities.Category
import com.example.template.pagespeed.domain.entities.PageSpeedResult
import com.example.template.pagespeed.domain.entities.ReportResult
import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.infrastructure.dataSources.PagespeedSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes

class PagespeedDataSourceImpl(
    private val client: HttpClient,
    private val pageSpeedUrl: Url = Url(
        host = "www.googleapis.com",
        protocol = "https",
        path = "/pagespeedonline/v5/runPagespeed"
    ),
    private val settingsDataStore: SettingsDataStore
) : PagespeedSource {

    override suspend fun get(
        url: String,
        strategy: String,
    ): ReportResult {
        if (url.isEmpty()) {
            throw IllegalArgumentException(
                "URL cannot empty"
            )
        }
        val apiKey: String = this.settingsDataStore.getString(
            key = API_KEY_SETTING_KEY,
            defaultValue = ""
        )
        if (apiKey.isEmpty()) {
            throw ApiKeyNotFoundException(
                "API Key cannot empty"
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
                    value = apiKey
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

        println(report)
        return ReportResult(
            url = url,
            jsonInText = report,
            lighthouseCategories = this.parseLighthouseCategories(
                jsonString = report
            )
        )
    }


    private fun parseLighthouseCategories(jsonString: String): Map<String, Category> {
        val json = Json {
            ignoreUnknownKeys =
                true // Isso é crucial para ignorar campos no JSON que não estão mapeados nas suas classes
            coerceInputValues =
                true // Isso ajuda a lidar com valores nulos para tipos não anuláveis, atribuindo valores padrão.
        }
        val result =
            json.decodeFromString<PageSpeedResult>(PageSpeedResult.serializer(), jsonString)
        return result.lighthouseResult.categories
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

        return statusCode == HttpStatusCode.OK
    }
}