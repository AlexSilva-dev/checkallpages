package com.example.template.pagespeed.api.dtos

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class AlternativesDtoApi @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid? = null,
    val answer: String,
    val isCorrect: Boolean
)
