package com.yiyongc.sbpktor.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRequest(
    val title: String,
    val publishedDate: Instant,
    val inventoryCount: Int
)