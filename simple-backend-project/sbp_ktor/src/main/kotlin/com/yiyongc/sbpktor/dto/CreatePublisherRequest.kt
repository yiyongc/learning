package com.yiyongc.sbpktor.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatePublisherRequest(
    val name: String,
)