package com.yiyongc.sbpktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGameRequest(
    val name: String?,
    val count: Int?,
)