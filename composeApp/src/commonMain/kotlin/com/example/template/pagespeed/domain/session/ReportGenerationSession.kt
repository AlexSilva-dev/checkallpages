package com.example.template.pagespeed.domain.session

import com.example.template.pagespeed.domain.useCases.GeneratePagespeedReportsUseCase
import com.example.template.pagespeed.domain.useCases.ProgressEvent
import com.example.template.pagespeed.ui.viewModels.AnalysisStatus
import com.example.template.pagespeed.ui.viewModels.PageAnalysisState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

/**
 * Singleton responsável por manter o estado da geração de relatórios
 * independentemente do ciclo de vida das telas (ViewModels).
 */
class ReportGenerationSession(
    private val generatePagespeedReportsUseCase: GeneratePagespeedReportsUseCase
) {
    // Escopo global para a sessão, não atrelado à UI
    private val sessionScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _progress = MutableStateFlow<List<PageAnalysisState>>(emptyList())
    val progress = _progress.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating = _isGenerating.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    fun startGeneration(url: String, apiKey: String?) {
        if (_isGenerating.value) return // Já está rodando

        _isGenerating.value = true
        _error.value = null
        _progress.value = emptyList()

        sessionScope.launch {
            try {
                generatePagespeedReportsUseCase.execute(
                    baseUrlString = url,
                    onProgress = { event ->
                        handleProgressEvent(event)
                    }
                )
                // Sucesso final
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isGenerating.value = false
            }
        }
    }

    private fun handleProgressEvent(event: ProgressEvent) {
        when (event) {
            is ProgressEvent.Started -> {
                _progress.value = event.totalUrls.map { PageAnalysisState(url = it) }
            }
            is ProgressEvent.UrlProcessing -> {
                updateUrlStatus(event.url, AnalysisStatus.PROCESSING)
            }
            is ProgressEvent.UrlFinished -> {
                val status = if (event.isSuccess) AnalysisStatus.COMPLETED else AnalysisStatus.ERROR
                updateUrlStatus(event.url, status)
            }
        }
    }

    private fun updateUrlStatus(url: String, newStatus: AnalysisStatus) {
        _progress.update { currentList ->
            currentList.map { item ->
                if (item.url == url) item.copy(status = newStatus) else item
            }
        }
    }

    fun clearSession() {
        if (!_isGenerating.value) {
            _progress.value = emptyList()
            _error.value = null
        }
    }
}
