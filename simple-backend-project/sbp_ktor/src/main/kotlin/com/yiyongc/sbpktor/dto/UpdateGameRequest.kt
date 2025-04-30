package com.yiyongc.sbpktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGameRequest(
    val title: String?,
    val inventoryCount: Int?,
)