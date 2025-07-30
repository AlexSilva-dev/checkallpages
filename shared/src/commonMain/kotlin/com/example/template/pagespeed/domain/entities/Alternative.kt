package com.example.template.pagespeed.domain.entities

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Alternative @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid? = null,
    val answer: String,
    val isCorrect: Boolean
)
