package com.yiyongc.sbpkotlin.dto

import java.time.LocalDateTime
import java.util.*

data class PublisherGameDto (
    val id: UUID,
    val name: String,
    val publishedDate: LocalDateTime,
)