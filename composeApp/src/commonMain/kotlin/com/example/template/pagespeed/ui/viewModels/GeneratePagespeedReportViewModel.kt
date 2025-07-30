package com.example.template.pagespeed.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.app.viewModels.UiState
import com.example.template.pagespeed.domain.useCases.GeneratePagespeedReportsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

data class GeneratePagespeedReportData @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class) constructor(
    val url: String = ""
)

class GeneratePagespeedReportViewModel(
    private val generatePagespeedReportsUseCase: GeneratePagespeedReportsUseCase
) : ViewModel(), KoinComponent {
    private val _generatePagespeedReportData = MutableStateFlow<GeneratePagespeedReportData>(GeneratePagespeedReportData())
    val generatePagespeedReportData: StateFlow<GeneratePagespeedReportData> = _generatePagespeedReportData.asStateFlow()

    private val _reportGenerationState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val reportGeneration = this._reportGenerationState.asStateFlow()

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    fun onChangeUrl(url: String) {
        viewModelScope.launch {
            this@GeneratePagespeedReportViewModel._generatePagespeedReportData.update {
                it.copy(
                    url = url
                )
            }
        }
    }

    fun onGenerateReports() {
        if (this._reportGenerationState.value is UiState.Loading) return

        viewModelScope.launch {
            this@GeneratePagespeedReportViewModel._reportGenerationState.value = UiState.Loading
            try {
                val result = this@GeneratePagespeedReportViewModel.generatePagespeedReportsUseCase.execute(
                    baseUrlString = this@GeneratePagespeedReportViewModel._generatePagespeedReportData.value.url,
                )
                this@GeneratePagespeedReportViewModel._reportGenerationState.value = UiState.Success(Unit)
            } catch (e: Exception) {
                this@GeneratePagespeedReportViewModel._reportGenerationState.value = UiState.Error(e.message)
            }
        }
    }

    /**
     * Action for state reset
     */
    fun onReportGenerationActionCompleted() {
        this._reportGenerationState.value = UiState.Idle
    }
}