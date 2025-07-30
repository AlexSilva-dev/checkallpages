package com.example.template.pagespeed.api.dtos.mappers


import com.example.template.pagespeed.api.dtos.AlternativesDtoApi
import com.example.template.pagespeed.api.dtos.QuizDtoApi
import com.example.template.pagespeed.domain.entities.Alternative
import com.example.template.pagespeed.domain.entities.Quiz
import kotlin.uuid.ExperimentalUuidApi

// --- Funções de Extensão para Converter DTOs da API para Entidades de Domínio ---

/**
 * Converte um AlternativesDtoApi para a entidade de domínio Alternatives.
 */
@OptIn(ExperimentalUuidApi::class)
fun AlternativesDtoApi.toDomain(): Alternative {
    return Alternative(
        id = this.id,
        answer = this.answer,
        isCorrect = this.isCorrect
    )
}

/**
 * Converte um QuizDtoApi para a entidade de domínio Quiz.
 */
@OptIn(ExperimentalUuidApi::class)
fun QuizDtoApi.toDomain(): Quiz {
    return Quiz(
        id = this.id,
        question = this.question,
        alternatives = this.alternatives.map { it.toDomain() } // Reutiliza a conversão de AlternativesDtoApi
    )
}
