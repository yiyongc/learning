package com.yiyongc.sbpktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class PublisherDto(
    val id: String,
    val name: String,
    val games: List<PublisherGameDto>,
)