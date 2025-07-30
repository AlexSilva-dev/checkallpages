package com.example.template.pagespeed.domain.useCases

import com.example.template.pagespeed.domain.entities.Url
import com.example.template.pagespeed.domain.entities.Urlset
import com.example.template.pagespeed.domain.ports.PagespeedRepository
import com.example.template.pagespeed.domain.ports.SitemapRepository
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

class GeneratePagespeedReportsUseCase(
    private val pagespeedRepository: PagespeedRepository,
    private val sitemapRepository: SitemapRepository,
) {

    /*
     * Função principal para iniciar o processo de geração de relatórios.
     * @param baseUrlString A URL base do site (ex: "https://www.exemplo.com").
     */
    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    suspend fun execute(baseUrlString: String) {
        val baseUrl: Url? = parseBaseUrl(baseUrlString)
        if (baseUrl == null) {
            println("Invalid base URL provided. Aborting report generation.")
            return
        }

        println("Processing base URL: ${baseUrl.toString()}")

        val urlset: Urlset? = getUrlFromSitemap(baseUrl)
        if (urlset == null) throw IllegalStateException("Urlset cannot null")


        generateReportsForUrls(
            urlset
        )

        println("PageSpeed report generation completed.")
    }


    /*
     * Padrão de Projeto: Factory Method (implícito)
     * Embora não seja um Factory Method formal de classe, a função atua como
     * uma "fábrica" para criar um objeto Url a partir de uma string.
     * Ela encapsula a lógica de construção, validando e padronizando a criação
     * de objetos Url a partir de dados brutos (strings).
     */
    private fun parseBaseUrl(baseUrlString: String): Url? {
        val tempParts: List<String> = baseUrlString.split("://")

        val protocol: String
        val rawUrl: String

        if (tempParts.size == 2) {
            protocol = tempParts[0]
            rawUrl = tempParts[1]
        } else {
            // Assume HTTPS if no protocol is explicitly provided
            protocol = "https"
            rawUrl = tempParts.getOrNull(0) ?: return null // If no parts, URL is invalid
        }

        val hostAndPathParts = rawUrl.split("/", limit = 2)
        val host: String = hostAndPathParts.getOrNull(0) ?: return null // Host cannot be null
        val path: String? = hostAndPathParts.getOrNull(1)

        return Url(host = host, protocol = protocol, path = path)
    }


    /*
     * Busca as URLs dos sitemaps individuais a partir de um sitemap de índice.
     * @param sitemapIndexUrl A URL do sitemap de índice (ex: sitemap_index.xml).
     * @return Uma lista de URLs de sitemaps individuais.
     *
     * Padrão de Projeto: Data Access Object (DAO) (indireto via Repository)
     * Esta função interage com o PagespeedRepository para buscar dados.
     * O Repository atua como um DAO, abstraindo a fonte de dados (neste caso,
     * o arquivo XML do sitemap) e fornecendo uma interface limpa para o Use Case.
     */
    private suspend fun getUrlFromSitemap(sitemapIndexUrl: Url): Urlset? {
        println("Attempting to retrieve sitemap index from: ${sitemapIndexUrl.toString()}")
        return try {
            this.sitemapRepository.getUrlsFromSitemap(url = sitemapIndexUrl)
        } catch (e: Exception) {
            throw Exception("Erro get sitemap page")
        }
    }


    /*
     * Encontra a URL do sitemap de páginas dentro de uma lista de sitemaps individuais.
     * @param individualSitemapUrls A lista de URLs de sitemaps individuais.
     * @return A URL do sitemap de páginas, ou null se não for encontrada.
     *
     * Padrão de Projeto: Strategy Pattern (implícito na busca)
     * A função 'find' encapsula uma estratégia de busca (encontrar a URL que
     * contém "sitemap_pages.xml"). Isso é um uso simples, mas mostra como
     * diferentes estratégias de filtragem poderiam ser aplicadas aqui.
     */
    private fun findPagesSitemap(individualSitemapUrls: List<Url>): Url? {
        // Here, we look for a URL containing "sitemap_pages.xml"
        // This is a common convention, but you could define other strategies
        return individualSitemapUrls.find { it.fullUrl().contains("sitemap_pages.xml") }
    }


    /*
     * Busca todas as URLs de páginas de um sitemap de páginas específico.
     * @param pagesSitemapUrl A URL do sitemap de páginas.
     * @return Uma lista de URLs de páginas.
     *
     * Padrão de Projeto: Data Access Object (DAO) (indireto via Repository)
     * Similar a 'fetchIndividualSitemaps', esta função usa o Repository
     * para obter URLs de um sitemap, mantendo a responsabilidade de acesso
     * a dados isolada.
     */
    private suspend fun fetchPageUrls(pagesSitemapUrl: Url): Urlset? {
        return try {
            this.sitemapRepository.getUrlsFromSitemap(pagesSitemapUrl)
        } catch (e: Exception) {
            throw Exception("Error get sitemap pages ")
        }
    }


    /*
     * Itera sobre uma lista de URLs e gera um relatório de PageSpeed para cada uma.
     * @param urls A lista de URLs para as quais gerar relatórios.
     *
     * Padrão de Projeto: Iterator Pattern
     * O 'forEach' implicitamente implementa o Iterator Pattern, permitindo
     * que a função processe cada elemento de uma coleção de forma uniforme,
     * sem expor a estrutura interna da coleção.
     *
     * Padrão de Projeto: Command Pattern (implícito no 'generateReport')
     * A chamada 'pagespeedRepository.generateReport(url)' pode ser vista
     * como uma forma simples do Command Pattern, onde a ação de "gerar relatório"
     * é encapsulada em uma chamada que pode ser executada para diferentes URLs.
     */
    private suspend fun generateReportsForUrls(urlset: Urlset) {
        var limit: Int = 0
        urlset.url.forEach { pageUrl ->
            if (limit>1) {
                return@forEach
            }
            limit = limit.inc()
            try {
                pagespeedRepository.generateReport(url = pageUrl.loc)
            } catch (e: Exception) {
                println("Failed to generate report for ${pageUrl}: ${e.message}")
            }
        }
        println("Report generation finished.")
    }
}