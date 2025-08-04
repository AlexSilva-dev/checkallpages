package com.example.template.pagespeed.domain.useCases

import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.ports.PagespeedRepository

class GetDomainPageSpeedReportUseCase(
    private val pagespeedRepository: PagespeedRepository,
) {
    suspend fun execute(): List<ReportPathsPageSpeed> {
        return this.pagespeedRepository.getDomains()
    }
}