package com.example.template.pagespeed.domain.entities

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Quiz @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid? = null,
    val question: String? = null,
    val alternatives: List<Alternative> = listOf<Alternative>()
)
