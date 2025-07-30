package com.example.template.pagespeed.api.dtos.mappers

import com.example.template.pagespeed.api.dtos.AlternativesDtoApi
import com.example.template.pagespeed.api.dtos.QuizDtoApi
import com.example.template.pagespeed.domain.entities.Alternative
import com.example.template.pagespeed.domain.entities.Quiz
import kotlin.uuid.ExperimentalUuidApi

// --- Funções de Extensão para Converter Entidades de Domínio para DTOs da API ---

/**
 * Converte uma entidade de domínio Alternatives para AlternativesDtoApi.
 */
@OptIn(ExperimentalUuidApi::class)
fun Alternative.toDtoApi(): AlternativesDtoApi {
    return AlternativesDtoApi(
        id = this.id,
        answer = this.answer,
        isCorrect = this.isCorrect
    )
}

/**
 * Converte uma entidade de domínio Quiz para QuizDtoApi.
 */
@OptIn(ExperimentalUuidApi::class)
fun Quiz.toDtoApi(): QuizDtoApi {
    return QuizDtoApi(
        id = this.id,
        question = this.question ?: throw IllegalArgumentException("Question cannot be null"),
        alternatives = this.alternatives.map { it.toDtoApi() }
    )
}
