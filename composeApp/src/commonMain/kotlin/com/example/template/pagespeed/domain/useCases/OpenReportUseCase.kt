package com.example.template.pagespeed.domain.useCases

import com.example.template.pagespeed.domain.ports.PagespeedRepository

class OpenReportUseCase(
    private val pagespeedRepository: PagespeedRepository
) {
    suspend fun execute(path: String) {
        this.pagespeedRepository.openReport(path)
    }
}