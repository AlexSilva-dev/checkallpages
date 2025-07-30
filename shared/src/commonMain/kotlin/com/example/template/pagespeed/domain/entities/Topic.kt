package com.example.template.pagespeed.domain.entities

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Topic @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class) constructor(
    val id: Uuid? = null,
    val text: String? = null,
    val quizzes: List<Quiz> = listOf<Quiz>(),
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null
)
