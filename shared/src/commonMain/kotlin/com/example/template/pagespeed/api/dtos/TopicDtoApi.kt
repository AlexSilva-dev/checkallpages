package com.example.template.pagespeed.api.dtos

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class TopicDtoApi @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class) constructor(
    val id: Uuid?,
    val text: String,
    val quizzes: List<QuizDtoApi>,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
