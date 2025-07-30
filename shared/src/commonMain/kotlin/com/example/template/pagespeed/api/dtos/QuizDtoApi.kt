package com.example.template.pagespeed.api.dtos

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class QuizDtoApi @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid? = null,
    val question: String,
    val alternatives: List<AlternativesDtoApi>
)
