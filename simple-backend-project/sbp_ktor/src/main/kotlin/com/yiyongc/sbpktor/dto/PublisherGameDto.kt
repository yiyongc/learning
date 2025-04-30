package com.yiyongc.sbpktor.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PublisherGameDto(
    val id: String,
    val title: String,
    val publishedDate: Instant,
)