package com.yiyongc.sbpktor.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    val id: String,
    val title: String,
    val inventoryCount: Int,
    val publishedDate: Instant,
    val publisher: String,
)