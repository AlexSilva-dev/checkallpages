package com.example.template.pagespeed.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.app.viewModels.UiState
import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.domain.useCases.GetDomainPageSpeedReportUseCase
import com.example.template.pagespeed.domain.useCases.GetReportsPathsUseCase
import com.example.template.pagespeed.domain.useCases.GetSitemapPathsPageSpeedReportUseCase
import com.example.template.pagespeed.domain.useCases.OpenReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

data class ReportsViewModelData(
    val onUpdateListState: UiState<Unit> = UiState.Idle,
    val domainPaths: List<ReportPathsPageSpeed> = listOf(),
    val sitemapPaths: MutableMap<String, List<ReportPathsPageSpeed>> = mutableMapOf(),
    val reportPaths: MutableMap<String, List<ReportPathsPageSpeed>> = mutableMapOf(),
)

class ReportsViewModel(
    private val getDomainPageSpeedReportUseCase: GetDomainPageSpeedReportUseCase,
    private val getSitemapPathsPageSpeedReportUseCase: GetSitemapPathsPageSpeedReportUseCase,
    private val getReportsPathsUseCase: GetReportsPathsUseCase,
    private val openReportUseCase: OpenReportUseCase,
) : ViewModel(), KoinComponent {
    private val _reportsViewModelData: MutableStateFlow<ReportsViewModelData> =
        MutableStateFlow(ReportsViewModelData())

    val reportsViewModelData: StateFlow<ReportsViewModelData> =
        this._reportsViewModelData.asStateFlow()

    init {
        this.onUpdateDomainPaths()
    }

    fun onUpdateDomainPaths() {
        this.viewModelScope.launch {

            val domains = this@ReportsViewModel
                .getDomainPageSpeedReportUseCase.execute()

            this@ReportsViewModel._reportsViewModelData.update {
                it.copy(
                    domainPaths = domains
                )
            }

        }
    }

    fun onGetReportPaths(path: String) {
        this.viewModelScope.launch {
            println("path viemodel: $path")
            val reports = this@ReportsViewModel.getReportsPathsUseCase.execute(
                path = path
            )
            this@ReportsViewModel._reportsViewModelData.update {
                val reportPaths = it.reportPaths
                reportPaths.set(
                    path,
                    reports
                )
                it.copy(
                    reportPaths = reportPaths
                )
            }
        }
    }

    fun onGetSitmapPaths(path: String) {
        this.viewModelScope.launch {
            val reports =
                this@ReportsViewModel.getSitemapPathsPageSpeedReportUseCase.execute(
                    path = path
                )
            this@ReportsViewModel._reportsViewModelData.update {
                val sitemaps = it.sitemapPaths
                sitemaps.set(
                    path,
                    reports
                )
                it.copy(
                    sitemapPaths = sitemaps
                )
            }
        }
    }

    fun onOpenFile(path: String) {
        viewModelScope.launch {
            this@ReportsViewModel.openReportUseCase.execute(
                path = path
            )
        }
    }
}