package com.example.template.pagespeed.domain.entities

import kotlinx.serialization.Serializable

data class ReportResult(
    val url: String,
    val jsonInText: String,
    val lighthouseCategories: Map<String, Category>
)

@Serializable
data class PageSpeedResult(
    val lighthouseResult: LighthouseResult
)

@Serializable
data class LighthouseResult(
    val categories: Map<String, Category>
)

@Serializable
data class Category(
    val id: String,
    val title: String,
    val score: Double?
)