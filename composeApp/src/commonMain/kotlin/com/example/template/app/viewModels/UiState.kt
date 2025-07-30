package com.example.template.app.viewModels

sealed class UiState<out T> {
    /**
     * Init state
     */
    data object Idle : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val message: String?) : UiState<Nothing>()
}