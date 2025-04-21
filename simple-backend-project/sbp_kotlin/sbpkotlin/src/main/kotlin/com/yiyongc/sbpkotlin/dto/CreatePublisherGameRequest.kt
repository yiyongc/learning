package com.yiyongc.sbpkotlin.dto

import java.time.LocalDateTime

data class CreatePublisherGameRequest (
    val name: String,
    val publishedDate: LocalDateTime,
    val inventoryCount: Int,
)