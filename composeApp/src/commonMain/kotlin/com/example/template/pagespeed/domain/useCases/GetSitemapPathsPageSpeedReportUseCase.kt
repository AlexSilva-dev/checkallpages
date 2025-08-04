package com.example.template.pagespeed.domain.useCases

import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.ports.PagespeedRepository

class GetSitemapPathsPageSpeedReportUseCase(
    private val pagespeedRepository: PagespeedRepository,
) {
    suspend fun execute(path: String): List<ReportPathsPageSpeed> {
        return this.pagespeedRepository.getSitemapPaths(
            path = path
        )
    }
}